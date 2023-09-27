package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BillboardTalentRelatedResponse implements Serializable {

    @ApiModelProperty(value = "榜单帅才推荐ID")
    private Long id;

    @ApiModelProperty(value = "帅才名称")
    private String name;

    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "学位")
    private String highestEdu;

    @ApiModelProperty(value = "学位显示名称")
    private String highestEduName;

    @ApiModelProperty(value = "专业方向")
    private String researchDirection;

    @ApiModelProperty(value = "研究成果: 取项目值")
    private String project;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double star;


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
}
