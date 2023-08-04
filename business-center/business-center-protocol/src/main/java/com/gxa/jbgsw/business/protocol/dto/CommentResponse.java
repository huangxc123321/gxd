package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CommentResponse implements Serializable {
    @ApiModelProperty(value = "评论id")
    private String id;

    @ApiModelProperty(value = "分享文章或视频ID")
    private Long shareId;

    @ApiModelProperty(value = "评论人像")
    private String avatar;

    @ApiModelProperty(value = "评论人昵称")
    private String nick;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "回复内容")
    private List<CommentResponse> children;


}
