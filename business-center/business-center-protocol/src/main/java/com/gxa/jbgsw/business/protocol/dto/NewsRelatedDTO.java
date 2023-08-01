package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewsRelatedDTO {

    @ApiModelProperty(value = "新闻ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

}
