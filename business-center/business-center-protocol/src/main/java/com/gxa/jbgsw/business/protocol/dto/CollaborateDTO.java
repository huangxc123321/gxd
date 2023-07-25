package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CollaborateDTO implements Serializable {

    @ApiModelProperty(value = "成果ID或者需求ID(帅才ID)")
    private Long pid;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type;

    @ApiModelProperty(value = "合作方式，用逗号分开")
    private String mode;

    @ApiModelProperty(value = "详细说明")
    private String detail;

    @ApiModelProperty(value = "合作方发起人ID", hidden = true)
    private Long launchUserId;

    @ApiModelProperty(value = "合作方发起人名称", hidden = true)
    private String launchUserName;

    @ApiModelProperty(value = "状态：0 待沟通  ...", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "合作发起时间", hidden = true)
    private Date effectAt;

    @ApiModelProperty(value = "成果拥有者ID", hidden = true)
    private Long harvestUserId;



}
