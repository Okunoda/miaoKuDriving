package com.example.hxds.bff.driver.feign;

import com.example.hxds.bff.driver.controller.form.SearchDriverTodayBusinessDataForm;
import com.example.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Okunoda 2023/11/19
 */
@FeignClient("hxds-odr")
public interface OdrServiceApi {

    @PostMapping("/order/searchDriverTodayBusinessData")
    R searchDriverTodayBusinessData(SearchDriverTodayBusinessDataForm form);
}
