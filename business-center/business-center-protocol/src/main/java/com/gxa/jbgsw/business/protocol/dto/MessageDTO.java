package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "消息类型：0 系统消息  1 技术咨询")
    private Integer type;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息关联第三方ID")
    private Long pid;

    @ApiModelProperty(value = "系统消息来源：0 揭榜申请 1 榜单推荐 2 合作发起")
    private Integer origin;

    @ApiModelProperty(value = "消息生成时间")
    private Date createAt;

    @ApiModelProperty(value = "消息ID")
    private Long id;

    @ApiModelProperty(value = "第三方名称")
    private String thirdName;

    @ApiModelProperty(value = "第三方头像")
    private String thirdAvatar;
}
