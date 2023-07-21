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

    @ApiModelProperty(value = "专业方向")
    private String researchDirection;

    @ApiModelProperty(value = "研究成果: 取项目值")
    private String project;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double sStar;

}
