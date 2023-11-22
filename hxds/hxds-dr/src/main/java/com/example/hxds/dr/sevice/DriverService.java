package com.example.hxds.dr.sevice;

import com.example.hxds.common.util.PageUtils;

import java.util.HashMap;
import java.util.Map;

public interface DriverService {

    String registerNewDriver(Map param);

    Long deleteDriver(Long id);

    Integer updateDriverAuth(Map<String, Object> form);

    String createDriverFaceModel(Long driverId, String photo);

    HashMap<String, Object> login(String code);

    HashMap<String, Object> searchDriverBaseInfo(Long driverId);

    PageUtils searchDriverByPage(Map<String, Object> form);
}
