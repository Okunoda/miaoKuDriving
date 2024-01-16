package com.example.hxds.cst.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.MicroAppUtil;
import com.example.hxds.cst.controller.form.QueryCustomerCarForm;
import com.example.hxds.cst.db.dao.CustomerDao;
import com.example.hxds.cst.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author okunoda
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao dao;
    @Resource
    private MicroAppUtil appUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String registerNewCustomer(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = appUtil.getOpenId(code);
        HashMap tempParam = new HashMap() {{
            put("openId", openId);
        }};
        if (dao.hasCustomer(tempParam) != 0) {
            throw new HxdsException("该微信无法注册");
        }

        param.put("openId", openId);
        dao.registerNewCustomer(param);
        return dao.searchCustomerId(openId);
    }

    @Override
    public String login(String code) {
        String openId = appUtil.getOpenId(code);
        return dao.login(openId);
    }

    @Override
    public Integer insertCar(Map<String, Object> mapParam) {
        return dao.insertCar(mapParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public Integer deleteCar(String carId) {
        return dao.deleteCar(carId);
    }

    @Override
    public ArrayList<Map<String, Object>> queryCustomerCar(QueryCustomerCarForm param) {
        return dao.queryCustomerCar(param);
    }
}