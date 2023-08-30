package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SearchNewsResponse implements Serializable {

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

    @JsonFormat(pattern = "MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date publishAt;

    @ApiModelProperty(value = "展示图片")
    private String picture;

}
