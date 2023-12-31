package com.example.hxds.bff.driver.feign;

import com.example.hxds.bff.driver.controller.form.*;
import com.example.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//FeignClient注解的value写司机子模块注册在nacos中的模块名
@FeignClient(value="hxds-dr")
public interface DrServiceApi {

    /**
     * 注解里面写需要访问的接口的访问路径
     * 此处接口方法的声明与接口实际实现的地方的声明需保持一致
     */
    @PostMapping("/driver/registerNewDriver")
    R registerNewDriver(RegisterNewDriverForm form);

    /**
     * 根据司机 id 将司机信息删除
     * @param id    司机 id
     * @return      司机 id
     */
    @PostMapping("/driver/deleteDriver")
    R deleteDriver(@RequestParam("id") Long id);

    @PostMapping("/driver/updateDriverAuth")
    R updateDriverAuth(UpdateDriverAuthForm form);

    @PostMapping("/driver/createDriverFaceModel")
    R createDriverFaceModel(CreateDriverModelForm form);

    @PostMapping("/driver/login")
    R login(LoginForm form);

    @PostMapping("/driver/searchDriverBaseInfo")
    R searchDriverBaseInfo(SearchDriverBaseInfoForm driverId);

    @PostMapping("/setting/searchDriverSettings")
    R searchDriverSettings(SearchDriverSettingsForm form);

    @PostMapping("/driver/searchDriverAuth")
    R searchDriverAuth(SearchDriverAuthForm form);

}
