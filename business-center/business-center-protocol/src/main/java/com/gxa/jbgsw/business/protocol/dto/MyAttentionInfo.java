package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MyAttentionInfo implements Serializable {

    @ApiModelProperty(value = "关注ID")
    private Long id;

    @ApiModelProperty(value = "名称（政府/企业/帅才）")
    private String name;

    @ApiModelProperty(value = "企业logo, 帅才的图片")
    private String logo;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "职称：字典中获取")
    private Integer professional;

    @ApiModelProperty(value = "职称名称")
    private String professionalName;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

    @ApiModelProperty(value = "研究成果")
    private String harvest;

    @ApiModelProperty(value = "关注类型： 0 政府 1 企业 2 帅才")
    private Integer type;

    @ApiModelProperty(value = "关注第三方ID， 用作后面得查询")
    private Long pid;

}
