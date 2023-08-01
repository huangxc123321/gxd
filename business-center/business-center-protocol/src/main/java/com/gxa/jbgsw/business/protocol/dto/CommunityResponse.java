package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommunityResponse implements Serializable {

    @ApiModelProperty(value = "分享ID")
    private Long id;

    @ApiModelProperty(value = "类型：0 文章  1 视频")
    private Integer type;

    @ApiModelProperty(value = "类型名称：0 文章  1 视频")
    private String typeName;

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "分享时间")
    private Date createAt;

    @ApiModelProperty(value = "视频链接地址")
    private String links;

    @ApiModelProperty(value = "预览数")
    private Integer views;

    @ApiModelProperty(value = "点赞数")
    private Integer likes;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;
}
