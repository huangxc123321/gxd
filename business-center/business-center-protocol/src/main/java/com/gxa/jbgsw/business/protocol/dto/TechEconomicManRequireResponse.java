package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 技术经纪人：需求派单列表
 */
@Data
public class TechEconomicManRequireResponse implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态： 状态： 0 待派单， 1推荐，2 已接受 , 3 已拒绝")
    private  Integer status;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "榜单发布单位")
    private String unitName;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double star;

    @ApiModelProperty(value = "相关成果")
    private String harvests;

    @ApiModelProperty(value = "相关帅才")
    private String talents;



}
