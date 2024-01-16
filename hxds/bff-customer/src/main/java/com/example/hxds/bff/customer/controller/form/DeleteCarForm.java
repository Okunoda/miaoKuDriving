package com.example.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Okunoda 2024/1/4
 */
@Data
@Schema(description = "删除乘客车辆信息参数类")
public class DeleteCarForm {

    @NotNull(message = "车辆id不能为空")
    @Min(value = 1, message = "车辆id不能小于1")
    @Schema(description = "车辆id")
    private String carId;
}
