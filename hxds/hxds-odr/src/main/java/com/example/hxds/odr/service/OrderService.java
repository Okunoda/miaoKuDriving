package com.example.hxds.odr.service;

import java.util.HashMap;

/**
 * @author Okunoda 2023/11/19
 */
public interface OrderService {
    HashMap<String, Object> searchDriverTodayBusinessData(Long driverId);

}
