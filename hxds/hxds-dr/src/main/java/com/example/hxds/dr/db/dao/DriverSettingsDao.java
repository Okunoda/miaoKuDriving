package com.example.hxds.dr.db.dao;

import com.example.hxds.dr.db.pojo.DriverSettingsEntity;

import java.util.Map;

/**
 * @Entity com.example.hxdsdr.db.pojo.DriverSettingsEntity
 */
public interface DriverSettingsDao {

    Integer insertDriverSettings(DriverSettingsEntity entity);

    Integer deleteDriverSettingByDriverId(Long id);
}




