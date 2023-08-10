package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardlifecycleDTO implements Serializable {

    @ApiModelProperty(value = "派单ID")
    private Long pid;

    @ApiModelProperty(value = "跟进描述")
    private String content;

    @ApiModelProperty(value = "状态： 0 未结束  1 结束")
    private Integer status;

    @ApiModelProperty(value = "跟进时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "跟进人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "跟进人姓名", hidden = true)
    private String userName;

    @ApiModelProperty(value = "附件，多个附件用 逗号分隔")
    private String attachments;

}
