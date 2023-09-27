package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardHarvestRelatedResponse implements Serializable {

    @ApiModelProperty(value = "成果ID")
    private Long id;

    @ApiModelProperty(value = "榜单成果推荐ID： 推荐的时候使用这个ID")
    private Long recommendId;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类名称")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词")
    private String techKeys;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain;

    @ApiModelProperty(value = "技术领域前端显示名称")
    private String techDomainName;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain1;

    @ApiModelProperty(value = "技术领域前端显示名称")
    private String techDomain1Name;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain2;

    @ApiModelProperty(value = "技术领域前端显示名称")
    private String techDomain2Name;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "成熟度名称：字典中获取")
    private String maturityLevelName;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double star;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Double hstar;

    @ApiModelProperty(value = "推荐人名称")
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "推荐时间")
    private Date recommendAt;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否推荐： 0 不推荐(取消) 1推荐")
    private Integer status;
}
