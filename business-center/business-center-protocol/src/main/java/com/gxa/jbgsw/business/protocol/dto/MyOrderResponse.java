package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyOrderResponse implements Serializable {

    @ApiModelProperty(value = "接单集合")
    List<TechEconomicManRequiresResponse> requires;

    @ApiModelProperty(value = "政府榜总数")
    long govs = 0;

    @ApiModelProperty(value = "企业榜总数")
    long buzs = 0;

    @ApiModelProperty(notes = "页数（默认1）")
    private Integer pageNum = 1;

    @ApiModelProperty(notes = "纪录数（默认10）")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("当前页的记录数量")
    private int size;

    @ApiModelProperty("总页数")
    private int pages;


}
