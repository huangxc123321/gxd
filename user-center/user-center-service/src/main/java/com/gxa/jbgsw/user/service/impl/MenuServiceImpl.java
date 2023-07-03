package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.mapper.MenuMapper;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.service.MenuService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    MapperFacade mapperFacade;
    @Resource
    MenuMapper menuMapper;


    @Override
    public List<MenuPO> getAllMenu() {
        // 先查询出pid = 0 的所有
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Menu::getPid, 0);
        lambdaQueryWrapper.orderByAsc(Menu::getShowInx);
        List<Menu> menus = menuMapper.selectList(lambdaQueryWrapper);
        List<MenuPO> menuPOList = mapperFacade.mapAsList(menus, MenuPO.class);

        menuPOList.stream().forEach(s->{
            Long pid = s.getId();
            LambdaQueryWrapper<Menu> sonWrapper = new LambdaQueryWrapper();
            sonWrapper.eq(Menu::getPid, pid);
            sonWrapper.orderByAsc(Menu::getShowInx);
            List<Menu> sonMenus = menuMapper.selectList(sonWrapper);
            List<MenuPO> mapAsList = mapperFacade.mapAsList(sonMenus, MenuPO.class);
            s.setSonMenus(mapAsList);
        });

        return menuPOList;
    }
}
