package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyPublishBillboardResponse implements Serializable {

    @ApiModelProperty(value = "榜单集合")
    List<MyPublishBillboardInfo> billboards;

    @ApiModelProperty(value = "我发布的政府榜单总数量")
    long govBillboardsNum = 0;

    @ApiModelProperty(value = "我发布的企业榜单总数量")
    long busBillboardsNum = 0;

    @ApiModelProperty(notes = "页数（默认1）")
    private Integer pageNum = 1;

    @ApiModelProperty(notes = "纪录数（默认10）")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private long total = 0L;

    @ApiModelProperty("当前页的记录数量")
    private int size = 0;

    @ApiModelProperty("总页数")
    private int pages = 0;

}
