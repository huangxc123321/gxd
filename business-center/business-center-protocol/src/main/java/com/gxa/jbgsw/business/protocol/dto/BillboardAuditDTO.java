package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardAuditDTO implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "审核状态： 0 待审核  1 审核通过  2 审核不通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @ApiModelProperty(value = "审核人ID", hidden = true)
    private Long auditUserId;

    @ApiModelProperty(value = "审核时间", hidden = true)
    private Date auditCreateAt;
}
