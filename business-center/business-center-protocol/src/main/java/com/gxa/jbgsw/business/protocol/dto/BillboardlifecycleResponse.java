package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardlifecycleResponse implements Serializable {

    @ApiModelProperty(value = "跟进ID")
    private Long id;

    @ApiModelProperty(value = "跟进描述")
    private String content;

    @ApiModelProperty(value = "状态： 0 未结束  1 结束")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "跟进时间")
    private Date createAt;

    @ApiModelProperty(value = "跟进显示时间")
    private String showDate;

    @ApiModelProperty(value = "跟进人")
    private Long createBy;

    @ApiModelProperty(value = "跟进人姓名")
    private String userName;

    @ApiModelProperty(value = "附件，多个附件用 逗号分隔")
    private String attachments;

}
