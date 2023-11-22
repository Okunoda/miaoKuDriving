package com.example.hxds.dr.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.PageUtil;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.common.util.R;
import com.example.hxds.dr.controller.form.*;
import com.example.hxds.dr.sevice.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("driver")
@Tag(name = "DriverController", description = "司机模块web接口")
public class DriverController {

    @Resource
    private DriverService driverService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "注册新司机")
    public R registerNewDriver(@RequestBody @Valid RegisterNewDriverForm form) {
        Map param = BeanUtil.beanToMap(form);
        String userId = driverService.registerNewDriver(param);
        return R.ok().put("userId", userId);
    }

    @PostMapping("/deleteDriver")
    public R deleteDriver(Long id) {
        Long userId = driverService.deleteDriver(id);
        return R.ok().put("userId", userId);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新司机实名信息")
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);

        Integer rows = driverService.updateDriverAuth(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/createDriverFaceModel")
    @Operation(summary = "创建司机人脸识别模型")
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverFaceModelForm form) {
        String msg = driverService.createDriverFaceModel(form.getDriverId(), form.getPhoto());
        return R.ok().put("msg", msg);
    }

    @PostMapping("login")
    @Operation(summary = "司机登录")
    public R login(@RequestBody @Valid LoginForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        String code = MapUtil.getStr(param, "code");
        HashMap<String, Object> login = driverService.login(code);
        return R.ok().put("result", login);
    }

    @PostMapping("searchDriverBaseInfo")
    @Operation(summary = "查询司机基本信息")
    public R searchDriverBaseInfo(@RequestBody @Valid SearchDriverBaseInfoForm form) {
        HashMap<String, Object> result = driverService.searchDriverBaseInfo(form.getDriverId());
        return R.ok().put("result", result);
    }

    @PostMapping("searchDriverByPage")
    @Operation(summary = "分页查询司机信息")
    public R searchDriverByPage(@RequestBody @Valid SearchDriverByPageForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        Integer page = form.getPage();
        Integer length = form.getLength();
        param.put("start", (page - 1) * length);
        PageUtils pageResult = driverService.searchDriverByPage(param);
        return R.ok().put("result", pageResult);
    }

    @PostMapping("searchDriverAuth")
    @Operation(summary = "查询司机实名认证信息")
    public R searchDriverAuth(@RequestBody @Valid SearchDriverAuthForm form) {
        HashMap<String, Object> result = driverService.searchDriverAuth(form);
        return R.ok().put("result", result);
    }


}
