package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页榜单DTO
 */
@Data
public class BillboardIndexDTO implements Serializable {

    @ApiModelProperty(value = "榜单ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "状态：待揭榜、已签约、解决中、已解决 (从字典中获取)", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "状态名称：待揭榜、已签约、解决中、已解决 (从字典中获取)", hidden = true)
    private String statusName;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类名称")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "发榜单位LOGO： 冗余字段，便于以后查询显示需要")
    private String unitLogo;

    @ApiModelProperty(value = "企业几颗星")
    private Integer star;
}
