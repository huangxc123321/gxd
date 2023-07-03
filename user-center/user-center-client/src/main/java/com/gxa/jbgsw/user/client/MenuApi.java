package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface MenuApi {

    @GetMapping("/menu/getAllMenu")
    public List<MenuPO> getAllMenu();

}
