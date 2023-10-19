package com.example.hxds.dr.sevice.impl;

import cn.hutool.core.map.MapUtil;
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
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
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
        tempParam.put("id",id);
        if(id == null || id.equals(0L) || driverDao.hasDriver(tempParam) == 0) {
            throw new HxdsException("id 参数不正确");
        }

        driverDao.deleteDriver(id);

        driverSettingsDao.deleteDriverSettingByDriverId(id);

        walletDao.deleteWalletByDriverId(id);

        return id;
    }
}
