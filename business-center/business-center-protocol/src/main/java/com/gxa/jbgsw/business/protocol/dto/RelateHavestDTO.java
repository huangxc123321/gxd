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

    @ApiModelProperty(value = "技术领域第三级")
    private String techDomain;

    @ApiModelProperty(value = "技术领域第一级")
    private String techDomain1;

    @ApiModelProperty(value = "技术领域第二级")
    private String techDomain2;

    @ApiModelProperty(value = "技术领域第三级（此接口，一级，二级，三级中文名统一放在这里）")
    private String techDomainName;

    @ApiModelProperty(value = "成果展示图")
    private String guidePicture;

}
