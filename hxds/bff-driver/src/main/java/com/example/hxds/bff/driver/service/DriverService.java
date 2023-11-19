package com.example.hxds.bff.driver.service;

import com.example.hxds.bff.driver.controller.form.*;

import java.util.HashMap;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    Long deleteDriver(Long id);

    Integer updateDriverAuth(UpdateDriverAuthForm form);

    String createDriverFaceModel(CreateDriverModelForm form);

    HashMap<String, Object> login(LoginForm form);

    HashMap<String, Object> searchDriverBaseInfo(SearchDriverBaseInfoForm driverId);
}
