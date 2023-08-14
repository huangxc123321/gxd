package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchParamsDTO implements Serializable {

    @ApiModelProperty(value = "搜索内容")
    private String searchFiled;

    @ApiModelProperty(value = "查询的条数：默认一条")
    private int pageNum = 1;

}
