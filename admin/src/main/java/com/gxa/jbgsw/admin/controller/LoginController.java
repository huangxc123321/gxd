package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.LoginFeignApi;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "用户登录管理")
@RestController
@Slf4j
@ResponseBody
public class LoginController extends BaseController {
    @Resource
    LoginFeignApi loginFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    UserResponse login(@RequestBody LoginRequest request) throws BizException, IllegalAccessException {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        log.info("------用户登录------- sessionId:{}", servletRequest.getSession().getId());

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