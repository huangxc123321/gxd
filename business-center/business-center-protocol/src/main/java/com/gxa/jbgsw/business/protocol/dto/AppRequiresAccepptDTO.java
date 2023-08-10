package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 需求是否接受DTO
 */
@Data
public class AppRequiresAccepptDTO implements Serializable {

    @ApiModelProperty(value = "需求派单ID")
    private Long id;

    @ApiModelProperty(value = "需求状态: 2 接受  3 不接受")
    private Integer status;

    @ApiModelProperty(value = "不接受理由")
    private String remark;
}
