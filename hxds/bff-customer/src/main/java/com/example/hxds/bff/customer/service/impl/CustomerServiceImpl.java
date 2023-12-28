package com.example.hxds.bff.customer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.bff.customer.controller.form.LoginParam;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.feign.CustomerApi;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.exception.HxdsException;
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

    @Override
    public Long login(LoginParam param) {
        R r = api.login(param.getCode());
        Long userId = MapUtil.getLong(r, "userId");
        if(userId == null || userId.equals(0L)){
            throw new HxdsException("未查找到当前用户");
        }
        return userId;
    }
}
