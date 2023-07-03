package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.user.client.RoleMenuApi;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;
import com.gxa.jbgsw.user.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@Api(tags = "角色资源关联管理")
public class RoleMenuController implements RoleMenuApi {
    @Autowired
    RoleMenuService roleMenuService;

    @Override
    @ApiOperation(value = "新增角色资源关联", notes = "新增角色资源关联")
    public void insert(RoleMenuDTO[] roleMenus) {
        roleMenuService.insert(roleMenus);
    }

    @Override
    public List<RoleMenuDTO> queryRoleOwnsMenus(Long roleId) {
        return roleMenuService.queryRoleOwnsMenus(roleId);
    }

    @Override
    public List<MenuPO> getUserMenus(Long userId, Integer platform) {
         return roleMenuService.getUserMenus(userId, platform);
    }


}

