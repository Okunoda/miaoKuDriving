package com.example.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.common.util.CosUtil;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.common.util.R;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
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

    @Resource
    private CosUtil cosUtil;

    @Override
    public PageUtils searchDriverByPage(SearchDriverByPageForm form) {
        R r = api.searchDriverByPage(form);
        HashMap resultMap = (HashMap) r.get("result");
        PageUtils result = BeanUtil.toBean(resultMap, PageUtils.class);
        return result;
    }

    @Override
    public HashMap<String, Object> searchDriverRealSummary(SearchDriverRealSummaryForm form) {
        HashMap<String, Object> result = new HashMap<>(6);
        if (form.getRealAuth() == 1) {
            return result;
        }
        R r = api.searchDriverRealSummary(form);
        HashMap map = (HashMap) r.get("result");
        String idcardFront = MapUtil.getStr(map, "idcardFront");
        String idcardBack = MapUtil.getStr(map, "idcardBack");
        String idcardHolding = MapUtil.getStr(map, "idcardHolding");
        String drcardFront = MapUtil.getStr(map, "drcardFront");
        String drcardBack = MapUtil.getStr(map, "drcardBack");
        String drcardHolding = MapUtil.getStr(map, "drcardHolding");

        ///获取私有文件的临时访问URL地址
        idcardFront = idcardFront.isEmpty() ? "" : cosUtil.getPrivateFileUrl(idcardFront);
        idcardBack = idcardBack.isEmpty() ? "" : cosUtil.getPrivateFileUrl(idcardBack);
        idcardHolding = idcardHolding.isEmpty() ? "" : cosUtil.getPrivateFileUrl(idcardHolding);
        drcardFront = drcardFront.isEmpty() ? "" : cosUtil.getPrivateFileUrl(drcardFront);
        drcardHolding = drcardHolding.isEmpty() ? "" : cosUtil.getPrivateFileUrl(drcardHolding);
        drcardBack = drcardBack.isEmpty() ? "" : cosUtil.getPrivateFileUrl(drcardBack);

        map.replace("idcardFront", idcardFront);
        map.replace("idcardBack", idcardBack);
        map.replace("idcardHolding", idcardHolding);
        map.replace("drcardFront", drcardFront);
        map.replace("drcardBack", drcardBack);
        map.replace("drcardHolding", drcardHolding);
        result.put("result", map);
        return result;
    }
}
