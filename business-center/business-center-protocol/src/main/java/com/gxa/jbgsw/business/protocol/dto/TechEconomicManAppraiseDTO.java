package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TechEconomicManAppraiseDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称: 如果匿名就为空")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "几颗星")
    private Integer star;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "是否匿名评价： ture 是， false 不是")
    private boolean anonymous = false;

    @ApiModelProperty(value = "经纪人ID")
    private Long techEconomicManId;

}
