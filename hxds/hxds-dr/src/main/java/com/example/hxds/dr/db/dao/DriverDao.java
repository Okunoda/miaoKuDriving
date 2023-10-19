package com.example.hxds.dr.db.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DriverDao {

    long hasDriver(Map param);

    int registerNewDriver(Map param);

    String searchDriverId(String openId);


    Integer deleteDriver(Long id);
}




