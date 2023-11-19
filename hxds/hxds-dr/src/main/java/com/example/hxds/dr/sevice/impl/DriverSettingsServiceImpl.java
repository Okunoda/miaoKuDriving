package com.example.hxds.dr.sevice.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.example.hxds.dr.db.dao.DriverSettingsDao;
import com.example.hxds.dr.sevice.DriverSettingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Okunoda 2023/11/19
 */
@Service
public class DriverSettingsServiceImpl implements DriverSettingsService {
    @Resource
    private DriverSettingsDao dao;

    @Override
    public HashMap<String, Object> searchDriverSettings(Long driverId) {
        String settings = dao.searchDriverSettings(driverId);
        HashMap<String, Object> result = JSONUtil.parseObj(settings).toBean(HashMap.class);
        boolean bool = MapUtil.getInt(result, "listenService") == 1;
        result.replace("listenService", bool);
        bool = MapUtil.getInt(result, "autoAccept") == 1;
        result.replace("autoAccept", bool);
        return result;
    }
}
