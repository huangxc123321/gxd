package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HarvestBillboardRelatedDTO implements Serializable {

    @ApiModelProperty(value = "榜单ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "榜单类型")
    private String type;

    @ApiModelProperty(value = "工信大类：字典中获取, type: categories")
    private Integer categories;

    @ApiModelProperty(value = "工信大类显示名称：字典中获取, type: categories")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double star;
}
