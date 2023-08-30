package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelateHavestDTO implements Serializable {
    @ApiModelProperty(value = "成果ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain;

    @ApiModelProperty(value = "技术领域中文显示名称")
    private String techDomainName;

    @ApiModelProperty(value = "成果展示图")
    private String guidePicture;

}
