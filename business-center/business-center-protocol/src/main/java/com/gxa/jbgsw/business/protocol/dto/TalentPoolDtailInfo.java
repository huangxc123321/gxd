package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TalentPoolDtailInfo implements Serializable {

    @ApiModelProperty(value = "帅才ID")
    private Long id;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "职称")
    private Long professional;

    @ApiModelProperty(value = "职称名称")
    private String professionalName;

    @ApiModelProperty(value = "专业方向")
    private String researchDirection;

    @ApiModelProperty(value = "研究成果")
    private String harvest;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "项目（JSON串，题目跟论文地址）")
    private String project;


    @ApiModelProperty(value = "论文（JSON串，题目跟论文地址）")
    private String treatise;

}
