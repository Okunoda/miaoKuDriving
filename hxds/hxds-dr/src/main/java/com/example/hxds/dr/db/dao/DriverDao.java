package com.example.hxds.dr.db.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DriverDao {

    long hasDriver(Map param);

    int registerNewDriver(Map param);

    String searchDriverId(String openId);


    Integer deleteDriver(Long id);

    Integer updateDriverAuth(Map<String, Object> param);

    Map<String, Object> queryDriverNameAndSex(Long driverId);

    int updateDriverArchive(Long driverId);

    HashMap<String, Object> login(String openId);
}




