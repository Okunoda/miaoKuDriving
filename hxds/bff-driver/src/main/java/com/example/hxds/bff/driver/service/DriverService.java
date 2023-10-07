package com.example.hxds.bff.driver.service;

import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);
}
