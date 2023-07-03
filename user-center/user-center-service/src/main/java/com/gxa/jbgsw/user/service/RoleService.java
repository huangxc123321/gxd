package com.gxa.jbgsw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.entity.Role;
import com.gxa.jbgsw.user.protocol.dto.RoleDTO;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.RoleRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface RoleService extends IService<Role> {

    void insert(RoleDTO roleDTO);

    void delete(Long id);

    PageResult<RolePO> pageQuery(RoleRequest request);

    void deleteBatchIds(Long[] ids);

    void updateRole(RoleDTO roleDTO);
}
