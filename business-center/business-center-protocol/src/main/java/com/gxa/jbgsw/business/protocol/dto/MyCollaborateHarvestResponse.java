package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyCollaborateHarvestResponse implements Serializable {

    @ApiModelProperty(value = "合作ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "发起人")
    private String launchUserName;

    @ApiModelProperty(value = "合作方式，用逗号分开")
    private String mode;

    @ApiModelProperty(value = "合作方式显示名称，用逗号分开")
    private String modeName;

    @ApiModelProperty(value = "详细说明")
    private String detail;

    @ApiModelProperty(value = "状态：0 待沟通  ...")
    private Integer status;

    @ApiModelProperty(value = "状态显示名称：0 待沟通  ...")
    private String statusName;

    @ApiModelProperty(value = "合作发起时间")
    private String effectAt;

    @ApiModelProperty(value = "合作方的联系电话")
    private String mobile;

    @ApiModelProperty(value = "合作申请拒绝原因")
    private String remark;
}