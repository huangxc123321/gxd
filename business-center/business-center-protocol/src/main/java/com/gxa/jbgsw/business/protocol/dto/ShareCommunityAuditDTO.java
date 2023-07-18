package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShareCommunityAuditDTO implements Serializable {

    @ApiModelProperty(value = "分享ID")
    private Long id;

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过")
    private Integer status;

    @ApiModelProperty(value = "是否审核通过原因")
    private String reason;

    @ApiModelProperty(value = "审核时间", hidden = true)
    private Date auditAt;

    @ApiModelProperty(value = "审核人ID", hidden = true)
    private Long auditUserId;

    @ApiModelProperty(value = "审核人名称", hidden = true)
    private String auditUserName;

}
