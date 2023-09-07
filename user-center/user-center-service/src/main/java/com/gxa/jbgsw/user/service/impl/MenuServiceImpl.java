package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.mapper.MenuMapper;
import com.gxa.jbgsw.user.protocol.dto.MenuDTO;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.service.MenuService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

            if(CollectionUtils.isNotEmpty(mapAsList)){
                mapAsList.stream().forEach(x->{
                    Long zpid = x.getId();
                    LambdaQueryWrapper<Menu> zWrapper = new LambdaQueryWrapper();
                    zWrapper.eq(Menu::getPid, zpid);
                    zWrapper.orderByAsc(Menu::getShowInx);
                    List<Menu> zMenus = menuMapper.selectList(zWrapper);
                    List<MenuPO> zmenuPOList = mapperFacade.mapAsList(zMenus, MenuPO.class);
                    x.setSonMenus(zmenuPOList);
                });
            }



        });

        return menuPOList;
    }

    @Override
    public void add(MenuDTO menuDTO) {
        Menu menu = mapperFacade.map(menuDTO, Menu.class);
        menu.setCreateAt(new Date());

        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) {
        Menu menu = mapperFacade.map(menuDTO, Menu.class);
        menu.setUpdateAt(new Date());

        menuMapper.updateById(menu);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        menuMapper.deleteBatchIds(list);
    }




/*
    @Override
    public List<MenuPO> getAllMenu() {
        return menuMapper.getMenuById(0L);
    }
*/


}
