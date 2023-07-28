package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyMessageRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "所有者", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "消息类型：0 系统消息  1 技术咨询")
    private Integer type;

}
