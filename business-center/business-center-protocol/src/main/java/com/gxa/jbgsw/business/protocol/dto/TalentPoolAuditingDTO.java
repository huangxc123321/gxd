package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TalentPoolAuditingDTO implements Serializable {

    @ApiModelProperty(value = "用户ID: 新增的時候传null, 编辑传用户ID")
    private Long id;

    @ApiModelProperty(value = "状态：0 正常， 1-注销")
    private Integer status;

    @ApiModelProperty(value = "审核是否通过原因")
    private String auditReason;

    @ApiModelProperty(value = "审核人ID", hidden = true)
    private Long auditUserId;

    @ApiModelProperty(value = "审核是否通过原因", hidden = true)
    private String auditUserName;

    @ApiModelProperty(value = "审核时间", hidden = true)
    private Date auditDate;


}
