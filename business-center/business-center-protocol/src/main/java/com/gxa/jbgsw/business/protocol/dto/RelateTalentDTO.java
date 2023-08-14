package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelateTalentDTO implements Serializable {

    @ApiModelProperty(value = "帅才ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "职称")
    private Long professional;

    @ApiModelProperty(value = "职称显示名称")
    private String professionalName;


    @ApiModelProperty(value = "研究方向")
    private String researchDirection;
}
