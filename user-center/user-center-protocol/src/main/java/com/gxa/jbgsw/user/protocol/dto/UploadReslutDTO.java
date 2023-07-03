package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UploadReslutDTO implements Serializable {

    @ApiModelProperty("返访问地址URL")
    private String url;
    @ApiModelProperty("文件名称")
    private String title;
}
