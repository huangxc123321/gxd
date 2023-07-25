package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchHavestResponse implements Serializable {


    @ApiModelProperty(value = "成果ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "成熟度名称")
    private String maturityLevelName;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "发榜单位LOGO： 冗余字段，便于以后查询显示需要")
    private String unitLogo;

    @ApiModelProperty(value = "成果简介")
    private String remark;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain;

}
