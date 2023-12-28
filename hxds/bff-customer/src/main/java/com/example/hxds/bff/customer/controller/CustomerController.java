package com.example.hxds.bff.customer.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.hxds.bff.customer.controller.form.LoginParam;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Okunoda 2023/12/18
 */
@RestController
@RequestMapping("customer")
@Tag(name = "CustomerController", description = "乘客端接口")
public class CustomerController {

    @Resource
    private CustomerService service;

    @PostMapping("registerNewCustomer")
    @Operation(description = "注册新乘客")
    public R registerNewCustomer(@RequestBody @Valid RegisterNewCustomerForm param) {
        Long userId = service.registerNewCustomer(param);
        return R.ok().put("userId", userId);
    }

    @PostMapping("login")
    @Operation(description = "乘客登录")
    public R login(@RequestBody @Valid LoginParam param) {
        Long userId = service.login(param);
        StpUtil.login(userId);
        String token = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token", token);
    }
}
