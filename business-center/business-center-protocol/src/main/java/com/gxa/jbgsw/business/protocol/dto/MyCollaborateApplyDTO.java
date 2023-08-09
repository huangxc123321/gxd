package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyCollaborateApplyDTO implements Serializable {

    @ApiModelProperty(value = "合作ID")
    private Long id;

    @ApiModelProperty(value = "状态：0 待沟通 1 合作申请同意  2 合作申请拒绝")
    private Integer status;

    @ApiModelProperty(value = "合作申请拒绝原因")
    private String remark;


}
