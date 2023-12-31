package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillboardTemporaryResponse implements Serializable {


    @ApiModelProperty(value = "榜单ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "工信大类：字典中获取, type: categories")
    private Integer categories;

    @ApiModelProperty(value = "工信大类显示字段")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔， 直接输入")
    private String techKeys;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

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

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "状态： 0 导入成功  1 导入失败")
    private Integer status;

    @ApiModelProperty(value = "状态显示名称")
    private String statusName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "发布人")
    private Long createBy;

}
