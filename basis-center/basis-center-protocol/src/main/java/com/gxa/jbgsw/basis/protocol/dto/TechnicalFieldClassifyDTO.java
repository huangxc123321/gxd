package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TechnicalFieldClassifyDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父级技术领域分类id：如果没有分类默认为-1")
    private Long pid;

    @ApiModelProperty(value = "技术领域分类名称")
    private String name;

    @ApiModelProperty(value = "技术领域分类子对象")
    List<TechnicalFieldClassifyDTO> children;

}
