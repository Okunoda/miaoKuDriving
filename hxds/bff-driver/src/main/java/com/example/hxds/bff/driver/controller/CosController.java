package com.example.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.hxds.bff.driver.controller.form.DeleteCosFileForm;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.CosUtil;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Okunoda
 */
@RestController
@RequestMapping("/cos")
@Slf4j
@Tag(name = "CosController",description = "对象存储Web接口")
public class CosController {
    @Resource
    private CosUtil cosUtil;


    /**
     * 上传文件到云端
     * @param file  上传的文件
     * @param module    上传的路径
     * @return
     */
    @PostMapping("uploadCosPrivateFile")
    @Operation(summary = "上传文件到云端")
    @SaCheckLogin
    public R uploadCosPrivateFile(@Param("file")MultipartFile file , @Param("module") String module){
        if (file.isEmpty()) {
            throw new HxdsException("上传的文件不能为空");
        }

        String path = null;
        if ("driverAuth".equals(module)) {
            path = "/driver/auth";
        } else {
            throw new HxdsException("module值不正确");
        }
        try {
            HashMap map = cosUtil.uploadPrivateFile(file, path);
            return R.ok(map);
        } catch (IOException e) {
            log.error("上传到云端错误",e);
            throw new HxdsException("上传文件时异常！请稍后再试");
        }
    }

    @PostMapping("deleteCosPrivateFile")
    @SaCheckLogin
    @Operation(summary = "删除云端文件")
    public R deleteCosPrivateFile(@Valid @RequestBody DeleteCosFileForm deleteCosFileForm){
        cosUtil.deletePrivateFile(deleteCosFileForm.getPatches());
        return R.ok();
    }
}
