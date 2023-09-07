package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecommendHavestResponse implements Serializable {


    @ApiModelProperty(value = "成果ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域显示名称(第一级) ")
    private String techDomain1Name;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第二级)")
    private String techDomain2Name;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "技术领域显示名称")
    private String techDomainName;


    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

}
