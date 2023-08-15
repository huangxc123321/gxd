package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 14:17
 * @Version 2.0
 */

@Data
public class NewsResponse implements Serializable {
    @ApiModelProperty(value = "新闻ID")
    private Long id;

    @ApiModelProperty(value = "新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态")
    private Integer type;

    @ApiModelProperty(value = "新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态")
    private String typeName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "地区范围")
    private String regionScope;

    @ApiModelProperty(value = "来源于")
    private String origin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date publishAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "发布者名称： 显示的时候，获取发布者名称")
    private String createName;

}
