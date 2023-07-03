package com.gxa.jbgsw.user.protocol.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel
@ToString
public class RolePO implements Serializable {
    @ApiModelProperty(value = "角色ID: 新增的時候传null, 编辑传角色ID")
    private Long id;
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;
    @ApiModelProperty("角色备注")
    private String remark;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;
}
