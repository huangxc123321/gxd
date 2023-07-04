package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Mr. huang
 * @Date 2023/7/4 0004 15:24
 * @Version 2.0
 */

@Data
public class FriendlyLinkDTO {

    @ApiModelProperty(value = "友情链接名称")
    private String name;

    @ApiModelProperty(value = "友情链接地址")
    private String link;

}
