package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.user.protocol.dto.UserRoleDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserRoleApi {

    @PostMapping("/userRole/insert")
    public void insert(@RequestBody UserRoleDTO[] userRoles);

}
