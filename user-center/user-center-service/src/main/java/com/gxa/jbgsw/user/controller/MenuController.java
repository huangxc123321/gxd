package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.user.client.MenuApi;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.protocol.dto.MenuDTO;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    MenuService menuService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<MenuPO> getAllMenu(){
        return menuService.getAllMenu();
    }

    @Override
    public void add(MenuDTO menuDTO) {
        menuService.add(menuDTO);
    }

    @Override
    public void update(MenuDTO menuDTO) {
        menuService.updateMenu(menuDTO);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        menuService.deleteBatchIds(ids);
    }

    @Override
    public MenuPO getById(Long id) {
        Menu menu = menuService.getById(id);
        return mapperFacade.map(menu, MenuPO.class);
    }
}

