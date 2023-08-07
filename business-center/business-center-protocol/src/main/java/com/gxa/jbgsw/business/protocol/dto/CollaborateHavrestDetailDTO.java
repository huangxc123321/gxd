package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CollaborateHavrestDetailDTO implements Serializable {
    @ApiModelProperty(value = "成果合作ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type = 0;

    @ApiModelProperty(value = "合作方式，用逗号分开, 名称见字典：collaborate_mode")
    private String  mode;

    @ApiModelProperty(value = "详细说明")
    private String detail;


}
