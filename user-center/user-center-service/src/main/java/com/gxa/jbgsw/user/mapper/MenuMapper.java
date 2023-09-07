package com.gxa.jbgsw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
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
public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuPO> getMenuById(@Param("parentId") Long id);
}
