package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardGainAuditDTO implements Serializable {

    @ApiModelProperty(value = "揭榜ID")
    private Long id;

    @ApiModelProperty(value = "揭榜审核状态：0 审核不通过  1 审核通过")
    private Integer auditingStatus;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @ApiModelProperty(value = "审核人", hidden = true)
    private Long auditingUserId;

    @ApiModelProperty(value = "审核人名称", hidden = true)
    private String auditingUserName;

}
