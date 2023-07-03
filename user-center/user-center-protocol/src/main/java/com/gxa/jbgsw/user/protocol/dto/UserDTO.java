package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(value = "用户ID: 新增的時候传null, 编辑传用户ID")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码")
    private String validateCode;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "性别： 0 男 1 女")
    private Integer sex;

    @ApiModelProperty(value = "省ID")
    private Long provinceId;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "创建者", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新", hidden = true)
    private Long updateBy;

    @ApiModelProperty(value = "密码" , hidden = true)
    private String password;


}
