package com.gxa.jbgsw.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.user.entity.UserRole;
import com.gxa.jbgsw.user.protocol.dto.UserRoleDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRoleResponse;

import java.util.List;


public interface UserRoleService extends IService<UserRole> {

    void insert(UserRoleDTO[] userRoles);

    List<UserRoleResponse> getUserRoleByUserIds(Long[] ids);
}
