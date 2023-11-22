package com.example.hxds.mis.api.service;

import com.example.hxds.common.util.PageUtils;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;

/**
 * @author Okunoda 2023/11/22
 */
public interface DriverService {

    PageUtils searchDriverByPage(SearchDriverByPageForm form);
}
