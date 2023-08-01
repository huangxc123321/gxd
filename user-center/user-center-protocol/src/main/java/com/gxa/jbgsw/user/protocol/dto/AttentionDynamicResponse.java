package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AttentionDynamicResponse implements Serializable {


    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "类型： 0 榜单 1 成果")
    private Integer type;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "发布时间")
    private Date createAt;

}
