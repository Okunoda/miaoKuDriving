package com.example.hxds.dr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "查询司机基本信息表单类")
@Data
public class SearchDriverBaseInfoForm {

    @Schema(description = "司机id")
    @Min(value = 1, message = "非法的司机id")
    @NotNull(message = "司机id不能为空")
    private Long driverId;

}
