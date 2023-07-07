package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyHarvestRequest extends PageRequest implements Serializable {
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long createBy;
}
