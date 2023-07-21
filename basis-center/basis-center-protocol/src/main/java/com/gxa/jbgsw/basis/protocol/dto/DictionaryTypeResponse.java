package com.gxa.jbgsw.basis.protocol.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ApiModel
@ToString
public class DictionaryTypeResponse   {

    @ApiModelProperty(value = "字典类型ID")
    private Long id;

    @ApiModelProperty(value = "字典类型名称")
    private String name;

    @ApiModelProperty(value = "字典类型的代码")
    private String code;

}
