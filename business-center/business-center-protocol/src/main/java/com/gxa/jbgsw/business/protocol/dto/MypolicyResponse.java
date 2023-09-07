package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MypolicyResponse implements Serializable {

    @ApiModelProperty(value = "收藏ID")
    private Long id;

    @ApiModelProperty(value = "相关收藏ID")
    private Long pid;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "来源于")
    private String from;

}
