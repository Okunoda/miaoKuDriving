package com.example.hxds.dr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "登录表单类")
public class LoginForm {
    @NotBlank(message = "小程序临时授权码为空")
    @Schema(description = "小程序生成的临时授权码")
    private String code;
}
