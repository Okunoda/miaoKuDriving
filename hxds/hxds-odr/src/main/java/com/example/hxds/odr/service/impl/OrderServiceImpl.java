package com.example.hxds.odr.service.impl;

import com.example.hxds.odr.db.dao.OrderDao;
import com.example.hxds.odr.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author Okunoda 2023/11/19
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao dao;

    @Override
    public HashMap<String, Object> searchDriverTodayBusinessData(Long driverId) {
        return dao.searchDriverTodayBusinessData(driverId);
    }
}
