package com.example.hxds.bff.driver.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.bff.driver.controller.form.CreateDriverModelForm;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
import com.example.hxds.bff.driver.feign.DrServiceApi;
import com.example.hxds.bff.driver.service.DriverService;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

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
}
