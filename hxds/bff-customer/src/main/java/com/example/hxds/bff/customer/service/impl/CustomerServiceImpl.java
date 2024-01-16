package com.example.hxds.bff.customer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.bff.customer.controller.form.InsertCarForm;
import com.example.hxds.bff.customer.controller.form.LoginParam;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.bff.customer.feign.CustomerApi;
import com.example.hxds.bff.customer.service.CustomerService;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

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

    @Override
    public Integer insertCar(InsertCarForm param) {
        return (Integer) api.insertCar(param).get("rows");
    }

    @Override
    public Integer deleteCar(String carId) {
        return (Integer) api.deleteCar(carId).get("rows");
    }

    @Override
    public ArrayList<Map<String, Object>> queryCustomerCar(String customerId) {
        return (ArrayList<Map<String, Object>>) api.queryCustomerCar(customerId).get("result");
    }


}
