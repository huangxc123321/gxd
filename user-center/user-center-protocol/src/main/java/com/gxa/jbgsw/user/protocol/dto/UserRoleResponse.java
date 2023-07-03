package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ApiModel
@ToString
public class UserRoleResponse implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Long id;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "用户所有的角色，多个中间用逗号分隔")
    private String roleNames;

}
