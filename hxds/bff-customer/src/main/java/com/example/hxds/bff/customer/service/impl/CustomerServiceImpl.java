package com.example.hxds.bff.customer.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.feign.CustomerApi;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Okunoda 2023/12/18
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerApi api;

    @Override
    public Long registerNewCustomer(@Valid RegisterNewCustomerForm param) {
        R r = api.registerNewCustomer(param);
        Long userId = MapUtil.getLong(r, "userId");
        return userId;
    }
}
