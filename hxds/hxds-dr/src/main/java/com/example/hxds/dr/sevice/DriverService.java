package com.example.hxds.dr.sevice;

import java.util.Map;

public interface DriverService {

    String registerNewDriver(Map param);

    Long deleteDriver(Long id);

    Integer updateDriverAuth(Map<String, Object> form);
}
