package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.user.client.MenuApi;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

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
@Api(tags = "资源管理")
public class MenuController implements MenuApi {
    @Autowired
    MenuService menuService;

    @GetMapping("/menu/getAllMenu")
    @ApiOperation(value = "获取所有资源", notes = "获取所有资源")
    public List<MenuPO> getAllMenu(){
        return menuService.getAllMenu();
    }
}

