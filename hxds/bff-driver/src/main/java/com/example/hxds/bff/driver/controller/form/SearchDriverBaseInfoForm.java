package com.example.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "查询司机基本信息表单类")
@Data
public class SearchDriverBaseInfoForm {

    @Schema(description = "司机id")
    private Long driverId;

}
