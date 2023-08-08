package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TechEconomicManDTO implements Serializable {

    @ApiModelProperty(value = "ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "专业标签，多个用英文逗号分开(生物与新医药、新材料、保鲜技术、智能终端、IC芯片技术、纳米技术、精细化工)")
    private String label;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Long level;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "经纪人类型：字典中获取, type: broker_type")
    private Integer type;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "省ID")
    private Long provinceId;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "发布时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "发布人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updateBy;

    @ApiModelProperty(value = "状态： 0 正常， 1 暂没发布" , hidden = true)
    private Integer status;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "或者协议")
    private String agreements;

    @ApiModelProperty(value = "需求派单：接单次数,系统向经纪人派单的次数")
    private Integer requires;

    @ApiModelProperty(value = "关注数: 有多少人关注了这个经纪人")
    private Integer attentions;

    @ApiModelProperty(value = "总促成:是指完成项目的总数量，完成项目以评价完成为准")
    private Integer successTotal;



}
