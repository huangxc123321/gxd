package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class RoleDTO implements Serializable {
    @ApiModelProperty(value = "角色ID: 新增的時候传null, 编辑传角色ID")
    private Long id;
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;
    @ApiModelProperty("角色备注")
    private String remark;
    @ApiModelProperty(value = "用户ID, 前端不需要看到这个字段", hidden = true)
    private Long userId;

}
