package com.example.hxds.mis.api.service;

import com.example.hxds.common.util.PageUtils;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
import com.example.hxds.mis.api.controller.form.UpdateDriverRealAuthForm;

import java.util.HashMap;

/**
 * @author Okunoda 2023/11/22
 */
public interface DriverService {

    PageUtils searchDriverByPage(SearchDriverByPageForm form);


    HashMap<String, Object> searchDriverRealSummary(SearchDriverRealSummaryForm form);

    Integer updateDriverRealAuth(UpdateDriverRealAuthForm form);
}
