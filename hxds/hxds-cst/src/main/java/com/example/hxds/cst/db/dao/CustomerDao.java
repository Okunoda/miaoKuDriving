package com.example.hxds.cst.db.dao;

import com.example.hxds.cst.controller.form.QueryCustomerCarForm;

import java.util.ArrayList;
import java.util.Map;

public interface CustomerDao {
    int registerNewCustomer(Map param);

    long hasCustomer(Map param);

    String searchCustomerId(String openId);

    String login(String openId);

    Integer insertCar(Map<String, Object> mapParam);

    Integer deleteCar(String carId);

    ArrayList<Map<String, Object>> queryCustomerCar(QueryCustomerCarForm param);
}

