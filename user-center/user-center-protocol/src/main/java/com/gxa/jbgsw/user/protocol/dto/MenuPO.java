package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@ToString
public class MenuPO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "URL地址")
    private String url;

    @ApiModelProperty(value = "子菜单")
    private List<MenuPO> sonMenus;

}
