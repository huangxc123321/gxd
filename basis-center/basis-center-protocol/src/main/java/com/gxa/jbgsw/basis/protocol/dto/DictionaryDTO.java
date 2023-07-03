package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class DictionaryDTO implements Serializable {
    @ApiModelProperty(value = "主键: 新增时不填写为null, 更新的时候需要用填写ID，不能为空")
    private Long id;

    @ApiModelProperty(value = "字典类型ID")
    private Long typeId;

    @ApiModelProperty(value = "字典代码")
    private String dicCode;

    @ApiModelProperty(value = "字典值")
    private String dicValue;

    @ApiModelProperty(value = "排序")
    private Integer showInx;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "用户ID, 前端不需要看到这个字段", hidden = true)
    private Long userId;

}
