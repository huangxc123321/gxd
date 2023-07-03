package com.gxa.jbgsw.user.controller;

import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.client.UserApi;
import com.gxa.jbgsw.user.entity.User;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "用户管理")
@RestController
@Slf4j
public class UserController implements UserApi {
    @Resource
    UserService userService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userService.getById(id);
        UserResponse userResponse = mapperFacade.map(user, UserResponse.class);

        return userResponse;
    }

    @Override
    public UserResponse getUserByCode(String code) {
        return userService.getUserByCode(code);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        userService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<UserResponse> pageQuery(UserRequest request) {
        return userService.pageQuery(request);
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = mapperFacade.map(userDTO, User.class);
        user.setCreateAt(new Date());

        userService.add(user);
    }

    @Override
    public void updateUseStatus(Long id, Integer useStauts) {
        userService.updateUseStatus(id, useStauts);
    }

    @Override
    public void update(UserDTO userDTO) {
        userService.updateUser(userDTO);
    }
}
