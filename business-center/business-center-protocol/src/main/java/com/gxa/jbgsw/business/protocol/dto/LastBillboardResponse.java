package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LastBillboardResponse implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单类型名称： 0 政府榜 1 企业榜")
    private String typeName;

    @ApiModelProperty(value = "发榜状态：待揭榜 1，攻关中 2，已完成 3")
    private Integer status;

    @ApiModelProperty(value = "发榜状态名称：待揭榜、已签约、解决中、已解决")
    private String statusName;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类名称")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "发榜单位")
    private String unitName;

    @ApiModelProperty(value = "排序")
    private Integer seqNo;

    @ApiModelProperty(value = "是否置顶： 0 不置顶 1 置顶")
    private Integer isTop;


}
