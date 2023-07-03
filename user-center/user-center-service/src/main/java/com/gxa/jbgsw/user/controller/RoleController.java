package com.gxa.jbgsw.user.controller;


import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.client.RoleApi;
import com.gxa.jbgsw.user.entity.Role;
import com.gxa.jbgsw.user.protocol.dto.RoleDTO;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.RoleRequest;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@RestController
@Slf4j
@Api(tags = "角色管理")
public class RoleController implements RoleApi {
    @Autowired
    RoleService roleService;
    @Resource
    MapperFacade mapperFacade;


    @Override
    @ApiOperation(value = "新增或者编辑角色信息", notes = "新增或者编辑角色信息")
    public void saveOrUpdate(RoleDTO roleDTO) {
        if(roleDTO.getId() == null){
            RoleRequest request = new RoleRequest();
            request.setName(roleDTO.getName());
            PageResult<RolePO> pageResult = roleService.pageQuery(request);
            if(pageResult != null && pageResult.getList() != null && pageResult.getList().size()>0){
                throw new BizException(UserErrorCode.ROLE_IS_EXIST);
            }
            roleService.insert(roleDTO);
        }else{
            roleService.updateRole(roleDTO);
        }
    }

    @Override
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    public PageResult<RolePO> pageQuery(RoleRequest request) {
        return roleService.pageQuery(request);
    }


    @Override
    @ApiOperation(value = "获取角色详细", notes = "获取角色详细")
    public RolePO getDepartmentById(Long id) {
        Role role = roleService.getById(id);
        return mapperFacade.map(role, RolePO.class);
    }

    @Override
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public void delete(Long id) {
        roleService.delete(id);
    }

    @Override
    @PostMapping(value = "/role/deleteBatchIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    public void deleteBatchIds(Long[] ids) {
        roleService.deleteBatchIds(ids);
    }

}

