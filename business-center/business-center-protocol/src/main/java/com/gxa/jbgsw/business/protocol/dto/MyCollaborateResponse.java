package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyCollaborateResponse implements Serializable {
    @ApiModelProperty(value = "合作ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "状态：0 待沟通  ...")
    private Integer status;

    @ApiModelProperty(value = "状态显示名称")
    private String statusName;

    @ApiModelProperty(value = "合作方式，用逗号分开, 名称见字典：collaborate_mode")
    private String  mode;

    @ApiModelProperty(value = "合作方式显示名称ß")
    private String  modeName;

    @ApiModelProperty(value = "详细说明")
    private String detail;

}
