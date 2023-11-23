package com.example.hxds.dr.db.dao;


import com.example.hxds.dr.controller.form.SearchDriverRealSummaryForm;

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

    HashMap<String, Object> searchDriverBaseInfo(Long driverId);

    ArrayList<HashMap> searchDriverByPage(Map param);

    Long searchDriverCount(Map param);


    HashMap<String, Object> searchDriverAuth(Long driverId);

    HashMap<String, Object> searchDriverRealSummary(SearchDriverRealSummaryForm form);
}




