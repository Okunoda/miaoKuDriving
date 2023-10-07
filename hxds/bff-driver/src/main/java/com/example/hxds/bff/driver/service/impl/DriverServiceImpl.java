package com.example.hxds.bff.driver.service.impl;

import cn.hutool.core.convert.Convert;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.feign.DrServiceApi;
import com.example.hxds.bff.driver.service.DriverService;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Service;

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
}
