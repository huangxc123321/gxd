package com.gxa.jbgsw.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;

import java.util.List;


public interface MenuService extends IService<Menu> {

    List<MenuPO> getAllMenu();

}
