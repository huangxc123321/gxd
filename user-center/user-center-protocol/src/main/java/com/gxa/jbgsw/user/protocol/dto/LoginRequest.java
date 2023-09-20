package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class LoginRequest implements Serializable {

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "密码：MD5加密")
    private String password;

    @ApiModelProperty(value = "验证码: 后台需要，APP不需要")
    private String validateCode;

    @ApiModelProperty(value = "扫码登录时的token")
    private String token;

    @ApiModelProperty(value = "登录方式： 0 账号+密码登录 1 账号+验证码登录 2 扫描二维码登录")
    private Integer loginWay = 0;

    @ApiModelProperty(value = "平台类型： 0 app , 1 后台", hidden = true)
    private Integer platform ;
}
