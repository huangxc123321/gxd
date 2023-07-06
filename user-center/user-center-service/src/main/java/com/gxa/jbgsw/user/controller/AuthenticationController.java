package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.client.AuthenticationApi;
import com.gxa.jbgsw.user.entity.Authentication;
import com.gxa.jbgsw.user.protocol.dto.AuthenticationDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.AuthenticationService;
import com.gxa.jbgsw.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
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
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(AuthenticationDTO authenticationDTO) {
        Authentication authentication = mapperFacade.map(authenticationDTO, Authentication.class);
        authenticationService.save(authentication);
    }

    @Override
    public AuthenticationDTO getAuthInfoByUserId(Long userId) {
        Authentication authentication = authenticationService.getAuthInfoByUserId(userId);

        if(authentication != null){
            AuthenticationDTO authenticationDTO = mapperFacade.map(authentication, AuthenticationDTO.class);
            return authenticationDTO;
        }

        return null;
    }
}

