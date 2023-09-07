package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.user.protocol.dto.MenuDTO;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuApi {

    @GetMapping("/menu/getAllMenu")
    public List<MenuPO> getAllMenu();

    @PostMapping("/menu/add")
    void add(@RequestBody MenuDTO menuDTO);

    @PostMapping("/menu/update")
    void update(@RequestBody MenuDTO menuDTO);

    @PostMapping(value = "/menu/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/menu/getById")
    MenuPO getById(@RequestParam("id") Long id);
}
