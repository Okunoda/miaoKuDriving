package com.example.hxds.cst.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Okunoda 2024/1/5
 */
@Schema
@Data
public class QueryCustomerCarForm {
    @NotNull(message = "乘客id不能为空")
    @Min(value = 1, message = "乘客id不能小于1")
    @Schema(description = "乘客id")
    private Long customerId;
}
