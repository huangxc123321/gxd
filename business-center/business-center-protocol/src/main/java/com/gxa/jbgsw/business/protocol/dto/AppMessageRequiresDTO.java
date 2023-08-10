package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppMessageRequiresDTO implements Serializable {

    @ApiModelProperty(value = "需求派单ID")
    private Long id;

    @ApiModelProperty(value = "榜单ID")
    private Long billboardId;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "派单时间")
    private Date createAt;

    @ApiModelProperty(value = "需求状态")
    private Integer status;

    @ApiModelProperty(value = "需求状态名称")
    private String statusName;

    @ApiModelProperty(value = "是否已读： 0 未读  1 已读")
    private Integer readFlag;
}
