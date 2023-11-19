package com.example.hxds.odr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Okunoda 2023/11/19
 */
@Data
@Schema(description = "查询今日订单信息参数类")
public class SearchDriverTodayBusinessDataForm {

    @Schema(description = "司机id")
    @Min(value = 1, message = "非法的司机id")
    @NotNull(message = "司机id不能为空")
    private Long driverId;

}
