package com.example.hxds.dr.controller;

import com.example.hxds.common.util.R;
import com.example.hxds.dr.controller.form.SearchDriverSettingsForm;
import com.example.hxds.dr.sevice.DriverSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author Okunoda 2023/11/19
 */
@RestController
@RequestMapping("setting")
@Tag(name = "DriverSettingController", description = "司机基础设置接口层")
public class DriverSettingController {

    @Resource
    private DriverSettingsService service;

    @PostMapping("searchDriverSettings")
    @Operation(description = "查询司机基础设置")
    public R searchDriverSettings(@RequestBody @Valid SearchDriverSettingsForm form) {
        HashMap<String, Object> result = service.searchDriverSettings(form.getDriverId());
        return R.ok().put("result", result);
    }
}
