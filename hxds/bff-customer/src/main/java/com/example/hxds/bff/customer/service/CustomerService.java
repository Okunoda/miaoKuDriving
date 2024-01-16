package com.example.hxds.bff.customer.service;

import com.example.hxds.bff.customer.controller.form.InsertCarForm;
import com.example.hxds.bff.customer.controller.form.LoginParam;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Okunoda 2023/12/18
 */
public interface CustomerService {
    Long registerNewCustomer(@Valid RegisterNewCustomerForm map);

    Long login(LoginParam param);

    Integer insertCar(InsertCarForm mapParam);

    Integer deleteCar(String carId);

    ArrayList<Map<String, Object>> queryCustomerCar(String customerId);
}
