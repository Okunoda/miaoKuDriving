package com.example.hxds.bff.driver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.bff.driver.controller.form.*;
import com.example.hxds.bff.driver.feign.DrServiceApi;
import com.example.hxds.bff.driver.feign.OdrServiceApi;
import com.example.hxds.bff.driver.service.DriverService;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public long registerNewDriver(RegisterNewDriverForm form) {
        R r = drServiceApi.registerNewDriver(form);
        return Convert.toLong(r.get("userId"));
    }

    @Override
    public Long deleteDriver(Long id) {
        R result = drServiceApi.deleteDriver(id);
        return Convert.toLong(result.get("userId"));
    }

    @Override
    public Integer updateDriverAuth(UpdateDriverAuthForm form) {
        R result = drServiceApi.updateDriverAuth(form);
        return Convert.toInt(result.get("rows"));
    }

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public String createDriverFaceModel(CreateDriverModelForm form) {
        R result = drServiceApi.createDriverFaceModel(form);
        return MapUtil.getStr(result, "msg");
    }

    @Override
    public HashMap<String, Object> login(LoginForm form) {
        R result = drServiceApi.login(form);
        return (HashMap<String, Object>) result.get("result");

    }

    @Override
    public HashMap<String, Object> searchDriverBaseInfo(SearchDriverBaseInfoForm form) {
        R result = drServiceApi.searchDriverBaseInfo(form);
        HashMap<String, Object> map = (HashMap<String, Object>) result.get("result");
        return map;
    }

    @Override
    public HashMap<String, Object> searchWorkbenchData(Long driverId) {
        //查询司机今日的营业数据
        SearchDriverTodayBusinessDataForm form1 = new SearchDriverTodayBusinessDataForm();
        form1.setDriverId(driverId);
        R todayBusinessDataResult = odrServiceApi.searchDriverTodayBusinessData(form1);
        HashMap todayBusinessData = (HashMap) todayBusinessDataResult.get("result");

        //查询司机的基本设置
        SearchDriverSettingsForm form2 = new SearchDriverSettingsForm();
        form2.setDriverId(driverId);
        R settingsResult = drServiceApi.searchDriverSettings(form2);
        HashMap settings = (HashMap) settingsResult.get("result");

        HashMap result = new HashMap(2) {
            {
                put("settings", settings);
                put("business", todayBusinessData);
            }
        };

        return result;
    }
}
