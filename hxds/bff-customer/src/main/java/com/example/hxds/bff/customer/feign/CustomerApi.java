package com.example.hxds.bff.customer.feign;

import com.example.hxds.bff.customer.controller.form.InsertCarForm;
import com.example.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.example.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Okunoda 2023/12/18
 */
@FeignClient("hxds-cst")
public interface CustomerApi {
    @PostMapping("customer/registerNewCustomer")
    R registerNewCustomer(RegisterNewCustomerForm param);

    @PostMapping("customer/login")
    R login(String code);

    @PostMapping("customer/insertCar")
    R insertCar(InsertCarForm param);

    @PostMapping("customer/deleteCar")
    R deleteCar(String carId);

    @PostMapping("customer/queryCustomerCar")
    R queryCustomerCar(String customerId);
}
