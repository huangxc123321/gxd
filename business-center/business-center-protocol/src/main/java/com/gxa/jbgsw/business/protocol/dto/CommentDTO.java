package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentDTO implements Serializable {

    @ApiModelProperty(value = "分享文章或视频ID")
    private Long shareId;

    @ApiModelProperty(value = "父级评论id：如果没有评论默认为-1")
    private String parentId;

    @ApiModelProperty(value = "评论人ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "评论人像", hidden = true)
    private String avatar;

    @ApiModelProperty(value = "评论人昵称", hidden = true)
    private String nick;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "发布时间", hidden = true)
    private Date createAt;

}
