package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttentionDTO implements Serializable {

    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @ApiModelProperty(value = "关注者ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "关注类型： 0 政府 1 企业 2 帅才 3 经纪人 ")
    private Integer type;

    @ApiModelProperty(value = "关联ID（政府版/企业榜ID或者帅才ID）")
    private Long pid;

    @ApiModelProperty(value = "关注状态： 0 关注， 1 取消关注")
    private Integer status;

    @ApiModelProperty(value = "收藏时间", hidden = true)
    private Date createAt;


}
