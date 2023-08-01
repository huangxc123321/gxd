package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QrConfirmLoginDTO implements Serializable {

    @ApiModelProperty("二维码ID")
    private String uuid;

    @ApiModelProperty("手机APP登录以后，保存的TOKEN")
    private String token;

    @ApiModelProperty("手机APP操作类型：2 确认， 5 取消 ")
    String code;

}
