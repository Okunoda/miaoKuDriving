package com.example.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
import com.example.hxds.bff.driver.service.DriverService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("driver")
@Tag(name = "DriverController", description = "司机模块Web接口")
public class DriverController {
    @Resource
    private DriverService driverService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "新司机注册")
    public R registerNewDriver(@RequestBody @Valid RegisterNewDriverForm form) {
        long driverId = driverService.registerNewDriver(form);
        //在SaToken上面执行登陆，实际上就是缓存userId，然后才有资格拿到令牌
        StpUtil.login(driverId);
        //生成Token令牌字符串（已加密）
        String token = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token", token);
    }

    @PostMapping("/deleteDriver")
    @Operation(summary = "删除司机相关信息")
    public R deleteDriver( Long id){
        Long driverId = driverService.deleteDriver(id);
        StpUtil.logout(driverId);
        return driverId > 0 ? R.ok():R.error();
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新司机实名信息")
    @SaCheckLogin
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form){
        //获取当前登录用户的 id
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        form.setDriverId(loginIdAsLong);
        Integer rows = driverService.updateDriverAuth(form);
        return R.ok().put("rows",rows);
    }
}
