package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.client.AuthenticationApi;
import com.gxa.jbgsw.user.entity.Authentication;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "认证管理")
@RestController
@Slf4j
public class AuthenticationController implements AuthenticationApi {
    @Resource
    AuthenticationService authenticationService;

    @Override
    public void deleteBatchIds(Long[] ids) {
        authenticationService.deleteBatchIds(ids);
    }

    void add(@RequestBody UserDTO userDTO) throws BizException {

    }

}

