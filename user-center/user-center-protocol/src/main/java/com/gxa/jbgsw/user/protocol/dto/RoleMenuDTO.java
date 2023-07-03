package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class RoleMenuDTO implements Serializable {

    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("资源ID")
    private Long menuId;
    @ApiModelProperty("是否有这个资源：true 有， false 没有")
    private boolean isOwns;

}
