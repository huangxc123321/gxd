package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author haungxc
 */
@Data
public class HarvestResponse implements Serializable {

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

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "成熟度中文显示名称")
    private String maturityLevelName;

    @ApiModelProperty(value = "技术持有人")
    private String holder;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "成果简介")
    private String remark;

    @ApiModelProperty(value = "收藏数")
    private Integer collectNum;

    @ApiModelProperty(value = "分享数")
    private Integer shareNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;


}
