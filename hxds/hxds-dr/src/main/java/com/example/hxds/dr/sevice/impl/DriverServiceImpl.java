package com.example.hxds.dr.sevice.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.MicroAppUtil;
import com.example.hxds.dr.db.dao.DriverDao;
import com.example.hxds.dr.db.dao.DriverSettingsDao;
import com.example.hxds.dr.db.dao.WalletDao;
import com.example.hxds.dr.db.pojo.DriverSettingsEntity;
import com.example.hxds.dr.db.pojo.WalletEntity;
import com.example.hxds.dr.sevice.DriverService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.CreatePersonRequest;
import com.tencentcloudapi.iai.v20200303.models.CreatePersonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Resource
    private MicroAppUtil microAppUtil;

    @Resource
    private DriverSettingsDao driverSettingsDao;

    @Resource
    private WalletDao walletDao;

    @Resource
    private DriverDao driverDao;

    @Value("${tencent.cloud.secretKey}")
    private String secretKey;

    @Value("${tencent.cloud.secretId}")
    private String secretId;

    @Value("${tencent.cloud.face.groupName}")
    private String groupName;

    @Value("${tencent.cloud.face.region}")
    private String region;

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String registerNewDriver(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);

        HashMap tempParam = new HashMap<>() {{
            put("openId", openId);
        }};
        //验证是否重复注册，查询是否存在记录
        if (driverDao.hasDriver(tempParam) != 0) {
            throw new HxdsException("该微信无法注册账号");
        }
        //将临时获取的 openId 永久的存储起来
        param.put("openId", openId);
        //放入司机的记录
        driverDao.registerNewDriver(param);
        //查询司机主键值
        String driverId = driverDao.searchDriverId(openId);

        //添加司机设置记录
        DriverSettingsEntity settingsEntity = new DriverSettingsEntity();
        settingsEntity.setDriverId(Long.parseLong(driverId));
        JSONObject json = new JSONObject();
        json.set("orientation", "");
        json.set("listenService", true);
        json.set("orderDistance", 0);
        json.set("rangeDistance", 5);
        json.set("autoAccept", false);
        settingsEntity.setSettings(json.toString());
        driverSettingsDao.insertDriverSettings(settingsEntity);

        //添加司机钱包记录
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setDriverId(Long.parseLong(driverId));
        walletEntity.setBalance(new BigDecimal("0"));
        //支付密码为空，用户支付的时候，系统会自动提示用户设置支付密码
        walletEntity.setPassword(null);
        walletDao.insert(walletEntity);

        return driverId;
    }

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long deleteDriver(Long id) {
        Map tempParam = new HashMap<>();
        tempParam.put("id", id);
        if (id == null || id.equals(0L) || driverDao.hasDriver(tempParam) == 0) {
            throw new HxdsException("id 参数不正确");
        }

        driverDao.deleteDriver(id);

        driverSettingsDao.deleteDriverSettingByDriverId(id);

        walletDao.deleteWalletByDriverId(id);

        return id;
    }

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateDriverAuth(Map<String, Object> param) {
        return driverDao.updateDriverAuth(param);
    }

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createDriverFaceModel(Long driverId, String photo) {
        //查询司机的名字和性别
        Map<String, Object> map = driverDao.queryDriverNameAndSex(driverId);
        String name = MapUtil.getStr(map, "name");
        String sex = MapUtil.getStr(map, "sex");

        //腾讯云创建人脸识别模型
        Credential credential = new Credential(secretId, secretKey);
        IaiClient client = new IaiClient(credential, region);
        try {
            CreatePersonRequest request = new CreatePersonRequest();
            request.setPersonId(driverId.toString());
            request.setGroupId(groupName);
            long gender = "男".equals(sex) ? 1L : 2L;
            request.setGender(gender);
            request.setPersonName(name);
            request.setImage(photo);
            //照片质量等级
            request.setQualityControl(4L);
            //重复人员识别等级
            request.setUniquePersonControl(4L);
            CreatePersonResponse response = client.CreatePerson(request);
            if (StrUtil.isNotBlank(response.getFaceId())) {
                //更新司机表的archive字段值
                int rows = driverDao.updateDriverArchive(driverId);
                if (rows != 1) {
                    return "更新司机归档字段失败";
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error("创建腾讯云端司机归档失败", e);
            return "创建腾讯云端司机归档失败";
        }
        //此处返回空对象的话会被自动的隐藏掉不进行传输
        return "";
    }
}
