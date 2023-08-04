package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 帅才详情
 */
@Data
public class TalentPoolDetailInfo implements Serializable {

    @ApiModelProperty(value = "帅才ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "职称")
    private Long professional;

    @ApiModelProperty(value = "职称中文名称")
    private String professionalName;

    @ApiModelProperty(value = "技术领域：字典")
    private Long techDomain;

    @ApiModelProperty(value = "技术领域中文名称")
    private String techDomainName;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

    @ApiModelProperty(value = "个人简介")
    private String remark;

    @ApiModelProperty(value = "论文（JSON串，题目跟论文地址）")
    private String treatise;

    @ApiModelProperty(value = "项目")
    private String project;

    @ApiModelProperty(value = "简历")
    private String curriculumVitae;

    @ApiModelProperty(value = "关注状态： 0 已关注  1 未关注")
    private Integer attentionStatus = 1;

}
