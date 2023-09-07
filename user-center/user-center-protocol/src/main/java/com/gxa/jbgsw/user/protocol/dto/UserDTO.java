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

    @ApiModelProperty(value = "单位性质： 1 政府部门 2 企业 3 科研机构/团队 4大学院校 5 个人")
    private Integer unitNature;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "单位LOGO")
    private String unitLogo;

    @ApiModelProperty(value = "职称")
    private String job;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "企业类型： 字典中获取， typeCode: enterprise_type")
    private Integer buzType;

    @ApiModelProperty(value = "行业: 字典中获取")
    private Integer tradeType;

    @ApiModelProperty(value = "单位简介")
    private String remark;

    @ApiModelProperty(value = "个人类型: 1 经纪人 2 帅才 0 其它")
    private Integer type ;
}
