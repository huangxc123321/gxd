package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RelateBillboardDTO implements Serializable {

    @ApiModelProperty(value = "榜单ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

}
