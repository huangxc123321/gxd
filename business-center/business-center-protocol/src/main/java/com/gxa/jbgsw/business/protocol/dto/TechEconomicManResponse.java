package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TechEconomicManResponse implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "专业标签，多个用英文逗号分开(生物与新医药、新材料、保鲜技术、智能终端、IC芯片技术、纳米技术、精细化工)")
    private String label;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Integer level;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "经纪人类型：字典中获取")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "授权时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "状态： 0 正常， 1 暂没发布")
    private Integer status;

    @ApiModelProperty(value = "分数 ")
    private BigDecimal score;

    @ApiModelProperty(value = "总促成成交数")
    private Integer successTotal;

    @ApiModelProperty(value = "人物照片")
    private String avatar;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "或者协议")
    private String agreements;

}
