package com.gxa.jbgsw.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.user.entity.Role;
import com.gxa.jbgsw.user.entity.UserRole;
import com.gxa.jbgsw.user.mapper.UserRoleMapper;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.UserRoleDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRoleResponse;
import com.gxa.jbgsw.user.service.RoleService;
import com.gxa.jbgsw.user.service.UserRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    RoleService roleService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void insert(UserRoleDTO[] userRolesList) {
        List<UserRoleDTO> list = Arrays.stream(userRolesList).collect(Collectors.toList());
        List<UserRole> userRoles = mapperFacade.mapAsList(list, UserRole.class);

        // 先删除该用户下的角色
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId, userRoles.get(0).getUserId());
        userRoleMapper.delete(lambdaQueryWrapper);

        // 批量新增
        this.saveBatch(userRoles);
    }

    @Override
    public List<UserRoleResponse> getUserRoleByUserIds(Long[] ids) {
        List<Long> list = new ArrayList<>();
        for(Long l : ids){
            list.add(l);
        }

        return userRoleMapper.getUserRoleByUserIds(list);
    }

    @Override
    public List<RolePO> getRoleByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId, userId);

        List<UserRole> userRoles = userRoleMapper.selectList(lambdaQueryWrapper);

        List<RolePO> roles = new ArrayList<>();
        if(userRoles != null && userRoles.size()>0 ){
            for(int i=0; i<userRoles.size(); i++){

                Role role = roleService.getById(userRoles.get(i).getRoleId());

                RolePO rolePO = mapperFacade.map(role, RolePO.class);

                roles.add(rolePO);
            }
        }

        return roles;
    }


}
