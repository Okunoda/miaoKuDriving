package com.example.hxds.odr.controller;

import com.example.hxds.common.util.R;
import com.example.hxds.odr.controller.form.SearchDriverTodayBusinessDataForm;
import com.example.hxds.odr.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author Okunoda 2023/11/19
 */
@RestController
@RequestMapping("/order")
@Tag(name = "orderController", description = "订单模块接口")
public class OrderController {
    @Resource
    private OrderService service;

    @PostMapping("/searchDriverTodayBusinessData")
    @Operation(description = "获取今日订单信息")
    public R searchDriverTodayBusinessData(@RequestBody @Valid SearchDriverTodayBusinessDataForm form) {
        HashMap<String, Object> map = service.searchDriverTodayBusinessData(form.getDriverId());
        return R.ok().put("result", map);
    }

}
