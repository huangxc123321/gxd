package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ShareCommunityDTO implements Serializable {
    @ApiModelProperty(value = "分享ID: 新增的時候传null, 编辑传用户ID")
    private Long id;

    @ApiModelProperty(value = "类型：0 文章  1 视频")
    private Integer type;

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享内容")
    private String content;

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "分享时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "分享人ID", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "分享人昵称", hidden = true)
    private String nick;

    @ApiModelProperty(value = "视频链接地址")
    private String links;

    @ApiModelProperty(value = "来自哪里")
    private String origin;

    @ApiModelProperty(value = "预览数", hidden = true)
    private Integer views;

    @ApiModelProperty(value = "点赞数", hidden = true)
    private Integer likes;

    @ApiModelProperty(value = "评论数", hidden = true)
    private Integer comments;

}
