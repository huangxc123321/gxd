package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserPlatformEnum;
import com.gxa.jbgsw.website.feignapi.LoginFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(tags = "用户登录管理")
@RestController
@Slf4j
@ResponseBody
public class LoginController extends BaseController {
    @Resource
    LoginFeignApi loginFeignApi;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    UserResponse login(@RequestBody LoginRequest request) throws BizException, IllegalAccessException {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        request.setPlatform(UserPlatformEnum.APP.getCode());
        UserResponse response = loginFeignApi.login(request);
        if(response != null){
            servletResponse.setHeader("token", response.getToken());
        }
        return response;
    }

    @ApiOperation("获取短信验证码")
    @GetMapping("/get/validate/code")
    void getValidateCode(@RequestParam("mobile") String mobile) throws Exception {
        loginFeignApi.getValidateCode(mobile);
    }


}