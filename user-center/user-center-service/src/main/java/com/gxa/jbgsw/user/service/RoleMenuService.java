package com.gxa.jbgsw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.user.entity.RoleMenu;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;

import java.util.List;


public interface RoleMenuService extends IService<RoleMenu> {

    void insert(RoleMenuDTO[] roleMenus);

    List<RoleMenuDTO> queryRoleOwnsMenus(Long roleId);

    List<MenuPO> getUserMenus(Long userId, Integer platform);
}
