package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MyPublishBillboardInfo implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @ApiModelProperty(value = "审核不通过原因")
    private String reason;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "审核不通过操作时间")
    private Date auditDate;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "企业logo")
    private String unitLogo;

    @ApiModelProperty(value = "发榜状态：待揭榜 1，攻关中 2，已完成 3")
    private Integer status;

    @ApiModelProperty(value = "发榜状态名称：待揭榜 1，攻关中 2，已完成 3")
    private String statusName;

    @ApiModelProperty(value = "pv值")
    private Integer pv;
}
