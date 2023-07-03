package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoleMenuApi {

    @PostMapping("/menu/insert")
    public void insert(@RequestBody RoleMenuDTO[] roleMenus);
    @GetMapping("/menu/queryRoleOwnsMenus")
    public List<RoleMenuDTO> queryRoleOwnsMenus(@RequestParam(value = "roleId") Long roleId);
    @GetMapping("/menu/getUserMenus")
    List<MenuPO> getUserMenus(@RequestParam(value = "userId") Long userId, @RequestParam(value = "platform", required=false)  Integer platform);
}
