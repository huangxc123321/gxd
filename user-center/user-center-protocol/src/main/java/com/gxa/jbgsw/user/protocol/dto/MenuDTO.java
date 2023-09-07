package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MenuDTO {

    @ApiModelProperty(value = "id , 新增的时候传 null" )
    private Long id;

    @ApiModelProperty(value = "pid， 最顶级为0")
    private Long pid;

    @ApiModelProperty(value = "排序")
    private Integer showInx;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "URL地址")
    private String url;

    @ApiModelProperty(value = "功能类型： 0 按钮 1 菜单")
    private Integer type;

    @ApiModelProperty(value = "菜单/按钮图形")
    private String icon;

    @ApiModelProperty(value = "创建者", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "权限标识")
    private String code;

    @ApiModelProperty(value = "前端组件")
    private String component;
}
