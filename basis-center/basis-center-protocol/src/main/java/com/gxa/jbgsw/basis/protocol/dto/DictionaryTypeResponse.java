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

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "用户ID, 前端不需要看到这个字段")
    private String code;

}
