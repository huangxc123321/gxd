package com.gxa.jbgsw.business.protocol.dto;

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

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享人昵称")
    private String nick;

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
    
}
