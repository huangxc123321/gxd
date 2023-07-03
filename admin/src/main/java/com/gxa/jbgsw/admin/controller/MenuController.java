package com.gxa.jbgsw.admin.controller;


import com.gxa.jbgsw.admin.feignapi.MenuFeignApi;
import com.gxa.jbgsw.admin.feignapi.RoleMenuFeignApi;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "资源管理")
@RestController
@Slf4j
public class MenuController {
    @Resource
    MenuFeignApi menuFeignApi;
    @Resource
    RoleMenuFeignApi roleMenuFeignApi;

    @GetMapping("/menu/getAllMenu")
    public List<MenuPO> getAllMenu(){
        return menuFeignApi.getAllMenu();
    }


    @PostMapping("/menu/insert")
    @ApiOperation(value = "增加角色资源", notes = "增加角色资源")
    public void insert(@RequestBody RoleMenuDTO[] roleMenus){
        roleMenuFeignApi.insert(roleMenus);
    }


    @GetMapping("/menu/queryRoleOwnsMenus")
    @ApiOperation(value = "获取角色的资源", notes = "获取角色的资源")
    public List<RoleMenuDTO> queryRoleOwnsMenus(@RequestParam(value = "roleId")  Long roleId){
        return roleMenuFeignApi.queryRoleOwnsMenus(roleId);
    }


    @GetMapping("/menu/getUserMenus")
    @ApiOperation(value = "获取用户的资源", notes = "获取用户的资源")
    public List<MenuPO> getUserMenus(@RequestParam(value = "userId")  Long userId){
        // platform: 0 标识后台
        return roleMenuFeignApi.getUserMenus(userId, 0);
    }

}
