package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateAdminPasswordDTO implements Serializable {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "新密码： md5加密")
    private String password;

}
