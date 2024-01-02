package com.example.hxds.mps.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxds.common.util.R;
import com.example.hxds.mps.controller.form.CalculateDrivingLineParam;
import com.example.hxds.mps.controller.form.EstimateOrderMileageAndMinuteParam;
import com.example.hxds.mps.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Okunoda 2023/12/29
 */
@RestController
@RequestMapping("map")
@Tag(name = "mapController", description = "地图服务接口类")
public class MapController {
    @Resource
    private MapService service;

    @PostMapping("calculateDrivingLine")
    @Operation(description = "计算行进路线")
    public R calculateDrivingLine(@RequestBody @Valid CalculateDrivingLineParam param){
        Map<String, Object> paramMap = BeanUtil.beanToMap(param);
        HashMap result = service.calculateDrivingLine(paramMap);
        return R.ok().put("result", result);
    }

    @PostMapping("estimateOrderMileageAndMinute")
    @Operation(description = "预估里程和时间")
    public R estimateOrderMileageAndMinute(@RequestBody @Valid EstimateOrderMileageAndMinuteParam param) {
        HashMap<String, Object> result = service.estimateOrderMileageAndMinute(param.getMode(),
                param.getStartPlaceLatitude(),
                param.getStartPlaceLongitude(),
                param.getEndPlaceLatitude(),
                param.getEndPlaceLongitude());

        return R.ok().put("result", result);
    }
}
