package com.gxa.jbgsw.user.mapper;

import com.gxa.jbgsw.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
public interface UserMapper extends BaseMapper<User> {

    List<UserResponse> pageQuery(UserRequest request);
}
