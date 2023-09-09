package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RelateEconomicDTO implements Serializable {

    @ApiModelProperty(value = "相关成果")
    List<RelateHavestDTO> havests = new ArrayList<>();

    @ApiModelProperty(value = "相关经纪人")
    List<TechEconomicManRecommondDTO> economics = new ArrayList<>();

}
