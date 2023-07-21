package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardHarvestRelatedResponse implements Serializable {

    @ApiModelProperty(value = "榜单成果推荐ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain;

    @ApiModelProperty(value = "技术领域前端显示名称")
    private String techDomainName;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "成熟度名称：字典中获取")
    private String maturityLevelName;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double sStar;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Double hStart;

    @ApiModelProperty(value = "推荐人名称")
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "推荐时间")
    private Date recommendAt;

    @ApiModelProperty(value = "备注")
    private String remark;


}
