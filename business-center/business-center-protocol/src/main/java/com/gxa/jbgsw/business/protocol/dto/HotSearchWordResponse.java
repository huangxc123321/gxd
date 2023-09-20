package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HotSearchWordResponse implements Serializable {

    @ApiModelProperty(value = "`热词")
    private String name;

    @ApiModelProperty(value = "`次数")
    private String total;

}
