package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class DictionaryCodeRequest implements Serializable {
    @ApiModelProperty(value = "字典类型代码", required = true)
    private String code;
}
