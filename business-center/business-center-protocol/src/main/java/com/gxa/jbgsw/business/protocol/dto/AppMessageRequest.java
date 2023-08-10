package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppMessageRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "查询类型: 0： 全部  1：未读  2 需求单")
    private Integer type;

}
