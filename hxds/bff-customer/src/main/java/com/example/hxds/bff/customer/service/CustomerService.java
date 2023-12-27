package com.example.hxds.bff.customer.service;

import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;

import javax.validation.Valid;

/**
 * @author Okunoda 2023/12/18
 */
public interface CustomerService {
    Long registerNewCustomer(@Valid RegisterNewCustomerForm map);
}
