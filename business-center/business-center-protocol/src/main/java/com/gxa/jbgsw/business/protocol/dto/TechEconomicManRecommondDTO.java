package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TechEconomicManRecommondDTO implements Serializable {

    @ApiModelProperty(value = "ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "专业标签，多个用英文逗号分开(生物与新医药、新材料、保鲜技术、智能终端、IC芯片技术、纳米技术、精细化工)")
    private String label;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Integer level;

    @ApiModelProperty(value = "等级显示名称：0 无， 1 金  2 银  3 铜")
    private String levelName;

    @ApiModelProperty(value = "总促成:是指完成项目的总数量，完成项目以评价完成为准")
    private Integer successTotal;

    @ApiModelProperty(value = "分数 ")
    private BigDecimal score;

}
