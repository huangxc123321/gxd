package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MyReceiveBillboardInfo implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "揭榜审核状态：0 待评审  1 已评审 2 未通过")
    private Integer auditingStatus;

    @ApiModelProperty(value = "揭榜审核状态名称")
    private String auditingStatusName;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "揭榜方案：附件")
    private String attachment;

    @ApiModelProperty(value = "审核人")
    private String auditingUserId;

    @ApiModelProperty(value = "审核时间")
    private Date applyAt;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;
}
