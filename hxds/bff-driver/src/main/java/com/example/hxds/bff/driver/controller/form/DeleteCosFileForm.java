package com.example.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author okunoda
 */
@Schema(description = "删除云端文件")
@Data
public class DeleteCosFileForm {
    @NotEmpty(message = "文件路径不能为空")
    @Schema(description = "文件路径集合")
    private String[] patches;
}
