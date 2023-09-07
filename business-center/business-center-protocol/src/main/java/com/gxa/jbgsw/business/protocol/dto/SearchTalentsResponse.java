package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchTalentsResponse implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "职称：字典中获取")
    private Integer professional;

    @ApiModelProperty(value = "职称名称")
    private String professionalName;

    @ApiModelProperty(value = "学历")
    private String highestEdu;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

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
