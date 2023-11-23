package com.example.hxds.mis.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Min;

/**
 * @author Okunoda 2023/11/23
 */
@Data
@Schema(description = "查询司机实名认证信息摘要")
public class SearchDriverRealSummaryForm {
    @NotNull(message = "driverId不能为空")
    @Min(value = 1, message = "driverId不能小于1")
    @Schema(description = "司机ID")
    private Long driverId;

    @NotNull(message = "实名认证状态不能为空")
    @Range(min = 1, max = 3, message = "实名认证状态非法")
    @Schema(description = "实名认证状态id")
    private Byte realAuth;

}
