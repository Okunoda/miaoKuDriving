package com.example.hxds.bff.driver.service;

import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    Long deleteDriver(Long id);

    Integer updateDriverAuth(UpdateDriverAuthForm form);
}
