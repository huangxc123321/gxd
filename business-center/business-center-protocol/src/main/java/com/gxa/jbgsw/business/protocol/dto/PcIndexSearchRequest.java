package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PcIndexSearchRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

}
