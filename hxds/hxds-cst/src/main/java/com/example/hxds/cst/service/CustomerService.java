package com.example.hxds.cst.service;

import com.example.hxds.cst.controller.form.QueryCustomerCarForm;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author okunoda
 */
public interface CustomerService {
    String registerNewCustomer(Map param);

    String login(String code);

    Integer insertCar(Map<String, Object> mapParam);

    Integer deleteCar(String carId);

    ArrayList<Map<String, Object>> queryCustomerCar(QueryCustomerCarForm param);

}
