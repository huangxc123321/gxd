package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HavestCollaborateDTO implements Serializable {

    @ApiModelProperty(value = "合作ID")
    private Long id;

    @ApiModelProperty(value = "合作类型：0 成果合作")
    private Integer type;

    @ApiModelProperty(value = "发起方")
    private Long launchUserId;

    @ApiModelProperty(value = "合作发起人名字")
    private String launchUserName;

    @ApiModelProperty(value = "详情")
    private String detail;

    @ApiModelProperty(value = "合作发起时间")
    private Date effectAt;

    @ApiModelProperty(value = "成果拥有者ID")
    private Long harvestUserId;

    @ApiModelProperty(value = "状态：0 待回复  ...")
    private Integer status;

    @ApiModelProperty(value = "合作方式")
    private String mode;

}
