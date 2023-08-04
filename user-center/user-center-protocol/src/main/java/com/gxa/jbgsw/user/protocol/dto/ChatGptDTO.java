package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatGptDTO implements Serializable {
    @ApiModelProperty(value = "发送消息")
    private String prompt;

    @ApiModelProperty(value = "热读", hidden = true)
    private double temperature = 0.7;

    @ApiModelProperty(value = "tokens", hidden = true)
    private int max_tokens = 30;
}
