package com.example.hxds.cst.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.common.util.R;
import com.example.hxds.cst.controller.form.RegisterNewCustomerForm;
import com.example.hxds.cst.service.CustomerService;
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
 * @author okunoda
 */
@RequestMapping("customer")
@RestController
@Tag(name = "乘客controller")
public class CustomerController {

    @Resource
    private CustomerService service;

    @Operation(description = "乘客注册")
    @PostMapping("/registerNewCustomer")
    public R regiserNewCustomer(@RequestBody @Valid RegisterNewCustomerForm param) {
        Map<String, Object> map = BeanUtil.beanToMap(param);
        String id = service.registerNewCustomer(map);
        return R.ok().put("id", id);
    }
}
