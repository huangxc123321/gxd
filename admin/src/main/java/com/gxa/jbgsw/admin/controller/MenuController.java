package com.gxa.jbgsw.admin.controller;


import com.gxa.jbgsw.admin.feignapi.MenuFeignApi;
import com.gxa.jbgsw.admin.feignapi.RoleMenuFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.business.protocol.dto.NewsDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.MenuDTO;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "资源管理")
@RestController
@Slf4j
public class MenuController extends BaseController {
    @Resource
    MenuFeignApi menuFeignApi;
    @Resource
    RoleMenuFeignApi roleMenuFeignApi;



    @ApiOperation("获取资源")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "资源ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/menu/getById")
    MenuPO getById(@RequestParam("id") Long id) throws BizException {
        MenuPO menuPO = menuFeignApi.getById(id);

        return menuPO;
    }



    @ApiOperation(value = "批量删除资源", notes = "批量删除资源")
    @PostMapping("/menu/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        menuFeignApi.deleteBatchIds(ids);
    }



    @ApiOperation(value = "新增资源", notes = "新增资源")
    @PostMapping("/menu/add")
    void add(@RequestBody MenuDTO menuDTO){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        menuDTO.setCreateBy(userId);

        menuFeignApi.add(menuDTO);
    }


    @ApiOperation(value = "更新资源", notes = "更新资源")
    @PostMapping("/menu/update")
    void update(@RequestBody MenuDTO menuDTO){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        menuDTO.setUpdateBy(userId);

        menuFeignApi.update(menuDTO);
    }



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
