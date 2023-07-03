package com.gxa.jbgsw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.user.entity.UserRole;
import com.gxa.jbgsw.user.protocol.dto.UserRoleResponse;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRoleResponse> getUserRoleByUserIds(List<Long> ids);
}
