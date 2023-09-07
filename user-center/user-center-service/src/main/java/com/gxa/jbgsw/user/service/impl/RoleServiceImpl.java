package com.gxa.jbgsw.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.entity.Role;
import com.gxa.jbgsw.user.entity.UserRole;
import com.gxa.jbgsw.user.mapper.RoleMapper;
import com.gxa.jbgsw.user.protocol.dto.RoleDTO;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.RoleRequest;
import com.gxa.jbgsw.user.service.RoleService;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    MapperFacade mapperFacade;
    @Resource
    RoleMapper roleMapper;
    
    @Override
    public void insert(RoleDTO roleDTO) {
        Role role = mapperFacade.map(roleDTO, Role.class);

        role.setCreateAt(new Date());
        role.setCreateBy(roleDTO.getUserId());

        roleMapper.insert(role);
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteById(id);
    }

    @Override
    public PageResult<RolePO> pageQuery(RoleRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StrUtil.isNotBlank(request.getName())){
            lambdaQueryWrapper.like(Role::getName, request.getName());
        }

        lambdaQueryWrapper.orderByAsc(Role::getCreateAt);
        List<Role> roles = roleMapper.selectList(lambdaQueryWrapper);

        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Role>>() {
        }.build(), new TypeBuilder<PageResult<RolePO>>() {}.build());
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        roleMapper.deleteBatchIds(list);
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        LambdaUpdateWrapper<Role> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Role::getName, roleDTO.getName())
                           .set(Role::getRemark, roleDTO.getRemark())
                           .set(Role::getUpdateAt, new Date())
                           .eq(Role::getId, roleDTO.getId());
        roleMapper.update(null, lambdaUpdateWrapper);
    }

}
