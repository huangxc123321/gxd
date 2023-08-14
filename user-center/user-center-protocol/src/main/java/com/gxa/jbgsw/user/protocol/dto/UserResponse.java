package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @author huangxc
 */
@Data
@ApiModel
@ToString
public class UserResponse implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "密码（md5）加密")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

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

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "使用状态： 0 已使用  1 停用")
    private Integer useStauts;

    /**
     * 通过认证来确定用户是什么类型的用户
     */
    @ApiModelProperty(value = "用户级别： 0 政府用户  1 企业  2 个人" )
    private Integer level;

/*    *//**
     * 通过认证来确定认证信息：比如：政府部门，企业单位名称
     *//*
    @ApiModelProperty(value = "认证信息" )
    private String authInfo;

    @ApiModelProperty(value = "单位ID")
    private Long companyId;*/

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "单位性质: 字典中获取， type: unit_nature")
    private Integer unitNature;

    @ApiModelProperty(value = "行业: 字典中获取, type: trade_type")
    private Integer tradeType;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "用户类型： 0 政府部门 1 企业 2 帅才 3 个人")
    private Integer type;
}
