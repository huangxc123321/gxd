package com.gxa.jbgsw.admin.controller;


import com.gxa.jbgsw.admin.feignapi.RoleFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserRoleFeignApi;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.RoleDTO;
import com.gxa.jbgsw.user.protocol.dto.RolePO;
import com.gxa.jbgsw.user.protocol.dto.RoleRequest;
import com.gxa.jbgsw.user.protocol.dto.UserRoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(tags = "角色管理")
@RestController
@Slf4j
public class RoleController extends BaseController {
    @Resource
    RoleFeignApi roleFeignApi;
    @Resource
    UserRoleFeignApi userRoleFeignApi;

    @PostMapping("/role/saveOrUpdate")
    @ApiOperation(value = "新增或修改角色信息", notes = "新增或修改角色信息")
    public void saveOrUpdate(@RequestBody RoleDTO roleDTO){
        roleDTO.setUserId(this.getUserId());
        roleFeignApi.saveOrUpdate(roleDTO);
    }

    @PostMapping("/role/pageQuery")
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    public PageResult<RolePO> pageQuery(@RequestBody RoleRequest request){
        PageResult<RolePO> pageResult = roleFeignApi.pageQuery(request);
        return pageResult;
    }

    @GetMapping("/role/getRoleById")
    @ApiOperation(value = "获取角色详细", notes = "获取角色详细")
    public RolePO getDepartmentById(@RequestParam("id") Long id){
        return roleFeignApi.getDepartmentById(id);
    }

    @GetMapping("/role/delete")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public void delete(@RequestParam("id") Long id){
        roleFeignApi.delete(id);
    }

    @PostMapping("/role/deleteBatchIds")
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    public void deleteBatchIds(@RequestBody Long[] ids){
        roleFeignApi.deleteBatchIds(ids);
    }


    @PostMapping("/userRole/insert")
    @ApiOperation(value = "增加用户角色", notes = "增加用户角色")
    public void insert(@RequestBody UserRoleDTO[] userRoles){
        userRoleFeignApi.insert(userRoles);
    }

}
