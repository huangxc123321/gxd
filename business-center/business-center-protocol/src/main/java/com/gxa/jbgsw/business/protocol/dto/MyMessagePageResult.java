package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyMessagePageResult implements Serializable {


    @ApiModelProperty(value = "榜单集合")
    List<MessageDTO> messages;

    @ApiModelProperty(value = "系统消息总数")
    Integer sysNum;

    @ApiModelProperty(value = "技术咨询消息总数")
    Integer tecNum;

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
