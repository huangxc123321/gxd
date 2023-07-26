package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardGainAddDTO implements Serializable {


    @ApiModelProperty(value = "相关榜单ID")
    private Long pid;

    @ApiModelProperty(value = "备注")
    private String remak;

    @ApiModelProperty(value = "揭榜方案：附件")
    private String attachment;

    @ApiModelProperty(value = "揭榜人", hidden = true)
    private String createByName;

    @ApiModelProperty(value = "揭榜单位", hidden = true)
    private String acceptBillboard;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间", hidden = true)
    private Date applyAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

}