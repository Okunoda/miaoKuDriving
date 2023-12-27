package com.example.hxds.bff.customer.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    @PostMapping("registermNewCustomer")
    @Operation(description = "注册新乘客")
    public R registerNewCustomer(@RequestBody @Valid RegisterNewCustomerForm param) {
        Long userId = service.registerNewCustomer(param);
        return R.ok().put("userId", userId);
    }
}
