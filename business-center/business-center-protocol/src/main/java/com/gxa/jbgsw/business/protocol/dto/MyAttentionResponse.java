package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyAttentionResponse implements Serializable {

    @ApiModelProperty(value = "榜单集合")
    List<MyAttentionInfo> billboards;

    @ApiModelProperty(value = "我关注的政府部门总数")
    Integer govNum = 0;

    @ApiModelProperty(value = "我关注的企业总数")
    Integer busBuzNum = 0;

    @ApiModelProperty(value = "我关注的帅才总数")
    Integer talentNum = 0;

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
