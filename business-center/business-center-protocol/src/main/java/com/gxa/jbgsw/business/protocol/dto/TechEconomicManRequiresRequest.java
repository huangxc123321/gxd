package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TechEconomicManRequiresRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "经纪人", hidden = true)
    private Long createBy;
}
