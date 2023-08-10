package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppMessageResponse implements Serializable {

    @ApiModelProperty(value = "所有未读标识: true 有未读")
    boolean allIsNoReadFlag = false;

    @ApiModelProperty(value = "未读标识: true 有未读")
    boolean noReadFlag = false;

    @ApiModelProperty(value = "需求未读标识: true 有未读")
    boolean requiresNoReadFlag = false;

    @ApiModelProperty(value = "消息")
    List<MessageDTO> messages;

    @ApiModelProperty(value = "需求单")
    List<AppMessageRequiresDTO> requires;

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
