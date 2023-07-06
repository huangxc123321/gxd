package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordDTO implements Serializable {

    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码")
    private String validateCode;

    @ApiModelProperty(value = "新密码： md5加密")
    private String password;

}
