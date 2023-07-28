package com.gxa.jbgsw.user.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户中心
 */
@Data
public class UserCenterDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "我的关注数")
    private Integer attentions;

    @ApiModelProperty(value = "我的收藏数")
    private Integer collections;

    @ApiModelProperty(value = "我的分享数")
    private Integer shareCommunitys;

    @ApiModelProperty(value = "我的消息数")
    private Integer messages;

    @ApiModelProperty(value = "我的积分")
    private Integer points;

    @ApiModelProperty(value = "关注动态")
    List<AttentionDynamicResponse> attentionDynamic;


}
