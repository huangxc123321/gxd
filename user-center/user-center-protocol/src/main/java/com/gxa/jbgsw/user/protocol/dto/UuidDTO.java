package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UuidDTO implements Serializable {

    @ApiModelProperty("服务器返回给PC的一个唯一标识，用于PC生成二维码")
    private String uuid;

    @ApiModelProperty(value = "二维码状态", hidden = true)
    private Integer status;


}
