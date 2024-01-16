package com.example.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Okunoda 2024/1/5
 */
@Schema
@Data
public class QueryCustomerCarForm {
    @Schema(description = "乘客id")
    private String customerId;
}
