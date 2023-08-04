package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatGptResponse implements Serializable {

    @ApiModelProperty(value = "返回内容")
    private String text;

    @ApiModelProperty(value = "索引")
    Integer index;


}
