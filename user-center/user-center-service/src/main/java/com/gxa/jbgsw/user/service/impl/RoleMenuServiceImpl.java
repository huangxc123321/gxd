package com.gxa.jbgsw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.user.entity.Menu;
import com.gxa.jbgsw.user.entity.RoleMenu;
import com.gxa.jbgsw.user.mapper.MenuMapper;
import com.gxa.jbgsw.user.mapper.RoleMenuMapper;
import com.gxa.jbgsw.user.protocol.dto.MenuPO;
import com.gxa.jbgsw.user.protocol.dto.RoleMenuDTO;
import com.gxa.jbgsw.user.service.RoleMenuService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void insert(RoleMenuDTO[] roleMenuArry) {
        List<RoleMenu> roleMenus = mapperFacade.mapAsList(roleMenuArry, RoleMenu.class);
        // 先删除该角色下的资源
        LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleMenu::getRoleId, roleMenus.get(0).getRoleId());
        roleMenuMapper.delete(lambdaQueryWrapper);
        // 批量新增
        this.saveBatch(roleMenus);
    }

    @Override
    public List<RoleMenuDTO> queryRoleOwnsMenus(Long roleId) {
        LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(RoleMenu::getId);
        // 所有的资源
        List<RoleMenu> allRoleMenus = roleMenuMapper.selectList(lambdaQueryWrapper);
        List<RoleMenuDTO> result = mapperFacade.mapAsList(allRoleMenus, RoleMenuDTO.class);

        // 已經分配的权限
        lambdaQueryWrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(lambdaQueryWrapper);

        result.stream().forEach(s->{
            Long meunId = s.getMenuId();
            roleMenus.stream().forEach(x->{
                Long mId = x.getMenuId();
                if(meunId.equals(mId)){
                    s.setOwns(true);
                }
            });
        });

        return result;
    }

    @Override
    public List<MenuPO> getUserMenus(Long userId, Integer platform) {
        if(platform == null){
            platform = 0;
        }

        List<Menu> menus = roleMenuMapper.getUserMenus(userId, platform);
        List<MenuPO> result =mapperFacade.mapAsList(menus, MenuPO.class);


//        Map<Long, Long> map = new HashMap<>();
//        menus.stream().forEach(s->{
//            if(!map.containsKey(s.getPid())){
//                // 没有包含
//                Menu menu = menuMapper.selectById(s.getPid());
//                if(menu != null){
//                    MenuPO po = mapperFacade.map(menu, MenuPO.class);
//                    result.add(po);
//                }
//            }
//        });

/*
        menus.stream().forEach(x->{
            Long id = x.getId();
            Long pid = x.getPid();
            Optional<MenuPO> first =  result.stream().filter(s->s.getId().equals(pid)).findFirst();
            if(first.isPresent()){
                MenuPO menuPO = first.get();
                List<MenuPO> list = menuPO.getSonMenus();
                if(CollectionUtils.isEmpty(list)){
                    list = new ArrayList<>();
                }

                MenuPO son = new MenuPO();
                son.setId(x.getId());
                son.setName(x.getName());
                son.setUrl(x.getUrl());

                list.add(son);
                menuPO.setSonMenus(list);
            }
        });
*/

        return result;
    }

}
