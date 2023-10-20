package com.example.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "创建司机人脸模型表单类")
public class CreateDriverModelForm {

    @Schema(description = "司机id")
    private Long driverId;

    @NotBlank(message = "照片不能为空")
    @Schema(description = "司机人脸照片的BASE64编码后字符串")
    private String photo;
}
