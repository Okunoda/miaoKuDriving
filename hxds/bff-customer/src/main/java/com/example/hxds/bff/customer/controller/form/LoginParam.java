package com.example.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Okunoda 2023/12/28
 */
@Schema(description = "登录参数类")
@Data
public class LoginParam {
    @NotBlank(message = "临时授权code不能为空")
    @Schema(description = "临时授权code")
    private String code;
}
