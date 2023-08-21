package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ShareCommunityDetailDTO implements Serializable {

    @ApiModelProperty(value = "分享ID")
    private Long id;

    @ApiModelProperty(value = "类型：0 文章  1 视频")
    private Integer type;

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享人昵称")
    private String nick;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "分享时间")
    private Date createAt;

    @ApiModelProperty(value = "分享内容")
    private String content;

    @ApiModelProperty(value = "预览数")
    private Integer views;

    @ApiModelProperty(value = "评论数", hidden = true)
    private Integer comments;

    @ApiModelProperty(value = "点赞数", hidden = true)
    private Integer likes;

    @ApiModelProperty(value = "分享内容的评论信息")
    List<CommentResponse> commentResponses = new ArrayList<>();

    @ApiModelProperty(value = "视频链接地址")
    private String links;

    @ApiModelProperty(value = "来自哪里")
    private String origin;


}
