package com.gxa.jbgsw.basis.protocol.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ApiModel
@ToString
public class DictionaryResponse {
    @ApiModelProperty(value = "字典ID")
    private Long id;
    @ApiModelProperty(value = "字典排序")
    private Integer showInx;
    @ApiModelProperty(value = "字典类型")
    private String typeId;
    @ApiModelProperty(value = "字典名称")
    private String dicCode;
    @ApiModelProperty(value = "字典值")
    private String dicValue;
    @ApiModelProperty(value = "字典值描述")
    private String remarks;

}
