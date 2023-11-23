package com.example.hxds.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.common.util.R;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
import com.example.hxds.mis.api.service.DriverService;
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
 * @author Okunoda 2023/11/22
 */
@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机管理Web接口")
public class DriverController {

    @Resource
    private DriverService drService;

    @PostMapping("/searchDriverByPage")
    @Operation(description = "分页查看司机信息")
    @SaCheckPermission(value = {"ROOT", "DRIVER:SELECT"}, mode = SaMode.OR)
    public R searchDriverByPage(@RequestBody @Valid SearchDriverByPageForm form) {
        PageUtils result = drService.searchDriverByPage(form);
        return R.ok().put("result", result);
    }

    @PostMapping("/searchDriverRealSummary")
    @Operation(description = "查询司机实名信息概述")
    @SaCheckPermission(value = {"ROOT", "DRIVER:SELECT"}, mode = SaMode.OR)
    public R searchDriverRealSummary(@RequestBody @Valid SearchDriverRealSummaryForm form) {
        HashMap<String, Object> result = drService.searchDriverRealSummary(form);
        return R.ok().put("result", result);
    }
}
