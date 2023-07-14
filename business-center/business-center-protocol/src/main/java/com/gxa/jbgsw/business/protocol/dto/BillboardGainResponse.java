package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 16:11
 * @Version 2.0
 */
@Data
public class BillboardGainResponse implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "相关榜单ID")
    private Long pid;

    @ApiModelProperty(value = "揭榜方案：附件")
    private String attachment;

    @ApiModelProperty(value = "揭榜单位")
    private String acceptBillboard;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间")
    private Date applyAt;

    @ApiModelProperty(value = "揭榜审核状态：0 审核不通过  1 审核通过")
    private Integer auditingStatus;

    @ApiModelProperty(value = "审核人")
    private Long auditingUserId;

    @ApiModelProperty(value = "审核人名称")
    private String auditingUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;


}
