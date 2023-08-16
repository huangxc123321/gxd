package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyReceiveBillboardResponse implements Serializable {

    @ApiModelProperty(value = "榜单集合")
    List<MyReceiveBillboardInfo> billboards;

    @ApiModelProperty(value = "我揭榜的政府榜单总数量")
    Long govBillboardsNum;

    @ApiModelProperty(value = "我揭榜的企业榜单总数量")
    Long busBillboardsNum;

    @ApiModelProperty(notes = "页数（默认1）")
    private Integer pageNum;

    @ApiModelProperty(notes = "纪录数（默认10）")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("当前页的记录数量")
    private int size;

    @ApiModelProperty("总页数")
    private int pages;

}
