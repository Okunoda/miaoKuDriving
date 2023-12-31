package com.example.hxds.mis.api.feign;

import com.example.hxds.common.util.R;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
import com.example.hxds.mis.api.controller.form.UpdateDriverRealAuthForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Okunoda 2023/11/22
 */
@FeignClient(value = "hxds-dr")
public interface DriverServiceApi {

    @PostMapping("/driver/searchDriverByPage")
    R searchDriverByPage(SearchDriverByPageForm form);


    @PostMapping("driver/searchDriverRealSummary")
    R searchDriverRealSummary(SearchDriverRealSummaryForm form);


    @PostMapping("/driver/updateDriverRealAuth")
    R updateDriverRealAuth(UpdateDriverRealAuthForm form);
}
