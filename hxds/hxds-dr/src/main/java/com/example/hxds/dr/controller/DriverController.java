package com.example.hxds.dr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.common.util.R;
import com.example.hxds.dr.controller.form.CreateDriverFaceModelForm;
import com.example.hxds.dr.controller.form.RegisterNewDriverForm;
import com.example.hxds.dr.controller.form.UpdateDriverAuthForm;
import com.example.hxds.dr.sevice.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
}
