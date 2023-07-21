package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BillboardEconomicRelatedResponse implements Serializable {

    @ApiModelProperty(value = "榜单经纪人推荐ID")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Long level;

    @ApiModelProperty(value = "等级显示名称")
    private String levelName;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "姓名")
    private String label;

    @ApiModelProperty(value = "总促成")
    private Integer successTotal;

    @ApiModelProperty(value = "系统推荐匹配度(分数)")
    private Double sStar;

}













