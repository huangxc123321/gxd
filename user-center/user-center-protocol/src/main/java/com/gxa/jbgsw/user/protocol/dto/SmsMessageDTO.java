package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SmsMessageDTO implements Serializable {

    @ApiModelProperty(value = "发送短信的号码")
    String mobile;
    @ApiModelProperty(value = "发送短信的类型：type: 0 验证, 1 其它")
    Integer type;
    @ApiModelProperty(value = "短信内容")
    String content;
}
