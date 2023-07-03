package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.user.client.UserRoleApi;
import com.gxa.jbgsw.user.protocol.dto.UserRoleDTO;
import com.gxa.jbgsw.user.service.UserRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@RestController
@Slf4j
@Api(tags = "用户角色关联管理")
public class UserRoleController implements UserRoleApi {
    @Autowired
    UserRoleService userRoleService;

    @Override
    public void insert(UserRoleDTO[] userRoles) {
        userRoleService.insert(userRoles);
    }
}

