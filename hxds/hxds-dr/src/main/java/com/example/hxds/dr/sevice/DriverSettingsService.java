package com.example.hxds.dr.sevice;

import java.util.HashMap;

/**
 * @author Okunoda 2023/11/19
 */
public interface DriverSettingsService {

    HashMap<String, Object> searchDriverSettings(Long driverId);
}
