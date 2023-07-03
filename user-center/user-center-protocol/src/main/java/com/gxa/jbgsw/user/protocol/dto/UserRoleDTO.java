package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class UserRoleDTO implements Serializable {

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;

}
