package com.example.hxds.cst.service;

import java.util.Map;

/**
 * @author okunoda
 */
public interface CustomerService {
    String registerNewCustomer(Map param);

    String login(String code);
}
