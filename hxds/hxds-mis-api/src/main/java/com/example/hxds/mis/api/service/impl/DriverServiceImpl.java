package com.example.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.common.util.R;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.feign.DriverServiceApi;
import com.example.hxds.mis.api.service.DriverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author Okunoda 2023/11/22
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DriverServiceApi api;

    @Override
    public PageUtils searchDriverByPage(SearchDriverByPageForm form) {
        R r = api.searchDriverByPage(form);
        HashMap resultMap = (HashMap) r.get("result");
        PageUtils result = BeanUtil.toBean(resultMap, PageUtils.class);
        return result;
    }
}
