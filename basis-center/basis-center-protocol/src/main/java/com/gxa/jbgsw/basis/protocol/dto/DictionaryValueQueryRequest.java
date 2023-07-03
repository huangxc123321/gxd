package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ApiModel
@ToString
public class DictionaryValueQueryRequest implements Serializable {
    @ApiModelProperty(value = "字典值code")
    private String code;
    @ApiModelProperty(value = "字典分類code")
    private String typeCode;

}
