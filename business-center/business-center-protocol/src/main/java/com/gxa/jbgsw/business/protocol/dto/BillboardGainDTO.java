package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardGainDTO implements Serializable {

    @ApiModelProperty(value = "揭榜ID")
    private Long id;

    @ApiModelProperty(value = "相关榜单ID")
    private Long pid;

    @ApiModelProperty(value = "备注")
    private String remak;

    @ApiModelProperty(value = "榜单名称")
    private String title;

    @ApiModelProperty(value = "揭榜人")
    private String createByName;

    @ApiModelProperty(value = "揭榜方案：附件")
    private String attachment;

    @ApiModelProperty(value = "揭榜单位")
    private String acceptBillboard;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间")
    private Date applyAt;

    @ApiModelProperty(value = "揭榜审核状态：0 审核不通过  1 审核通过")
    private Integer auditingStatus;

    @ApiModelProperty(value = "揭榜审核状态名称：0 审核不通过  1 审核通过")
    private String auditingStatusName;

    @ApiModelProperty(value = "审核人")
    private Long auditingUserId;

    @ApiModelProperty(value = "审核人名称")
    private String auditingUserName;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;



}
