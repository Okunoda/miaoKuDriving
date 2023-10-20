package com.example.hxds.dr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "创建司机人脸识别模型表单类")
public class CreateDriverFaceModelForm {

    @NotNull(message = "司机id不能为空")
    @Schema(description = "司机id")
    @Min(value = 1, message = "司机id不能小于1")
    private Long driverId;

    @NotBlank(message = "照片不能为空")
    @Schema(description = "人脸识别图片BASE64编码")
    private String photo;
}
