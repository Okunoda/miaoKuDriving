package com.example.hxds.mps.service;

import java.util.HashMap;

/**
 * @author Okunoda 2023/12/29
 */
public interface MapService {
    HashMap<String, Object> estimateOrderMileageAndMinute(String mode,
                                                          String startPlaceLatitude,
                                                          String startPlaceLongitude,
                                                          String endPlaceLatitude,
                                                          String endPlaceLongitude);
}
