package com.gxa.jbgsw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Menu> getUserMenus(@Param(value = "userId") Long userId, @Param(value = "platform")Integer platform);
}
