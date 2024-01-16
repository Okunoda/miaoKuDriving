package com.example.hxds.bff.customer.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.bff.customer.controller.form.*;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

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

    @PostMapping("insertCar")
    @Operation(description = "添加乘客车辆信息")
    public R insertCar(@RequestBody @Valid InsertCarForm param) {
        long customerId = StpUtil.getLoginIdAsLong();
        param.setCustomerId(customerId);
        Integer result = service.insertCar(param);
        return R.ok().put("rows", result);
    }

    @PostMapping("deleteCar")
    @Operation(description = "删除乘客车辆信息")
    public R deleteCar(@RequestBody @Valid DeleteCarForm param) {
        Integer result = service.deleteCar(param.getCarId());
        return R.ok().put("rows", result);
    }

    @PostMapping("searchCustomerCarList")
    @Operation(description = "查询乘客的所有车辆信息")
    public R searchCustomerCarList() {
        String customerId = StpUtil.getLoginIdAsString();
        ArrayList<Map<String, Object>> result = service.queryCustomerCar(customerId);
        return R.ok().put("result", result);
    }
}
