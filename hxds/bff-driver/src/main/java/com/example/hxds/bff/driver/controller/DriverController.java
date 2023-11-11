package com.example.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.bff.driver.controller.form.CreateDriverModelForm;
import com.example.hxds.bff.driver.controller.form.LoginForm;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
import com.example.hxds.bff.driver.service.DriverService;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

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
    public R deleteDriver(Long id) {
        Long driverId = driverService.deleteDriver(id);
        StpUtil.logout(driverId);
        return driverId > 0 ? R.ok() : R.error();
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新司机实名信息")
    @SaCheckLogin
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        //获取当前登录用户的 id
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        form.setDriverId(loginIdAsLong);
        Integer rows = driverService.updateDriverAuth(form);
        return R.ok().put("rows", rows);
    }


    @PostMapping("createDriverFaceModel")
    @SaCheckLogin
    @Operation(summary = "创建司机人脸识别模型")
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverModelForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);

        //请求调用创建人脸模型
        String resultMsg = driverService.createDriverFaceModel(form);
        return R.ok().put("result", resultMsg);
    }

    @PostMapping("login")
    @Operation(summary = "司机登录功能")
    public R login(@RequestBody @Valid LoginForm form) {
        HashMap<String, Object> map = driverService.login(form);
        if (map == null) {
            throw new HxdsException("登录失败");
        }
        Long driverId = MapUtil.getLong(map, "driverId");
        byte realAuth = Byte.parseByte(MapUtil.getStr(map, "realAuth"));
        Boolean archive = MapUtil.getBool(map, "archive");
        StpUtil.login(driverId);
        String token = StpUtil.getTokenValue();
        return R.ok().put("token", token).put("realAuth", realAuth).put("archive", archive);
    }

    @GetMapping("logout")
    @Operation(description = "司机登录注销功能")
    @SaCheckLogin
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }
}
