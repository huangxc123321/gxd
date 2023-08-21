package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileDTO implements Serializable {

    @ApiModelProperty(value = "文件标题")
    private String title;

    @ApiModelProperty(value = "文件地址")
    private String url;

}
