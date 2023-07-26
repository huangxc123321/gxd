package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CollaborateDTO implements Serializable {

    @ApiModelProperty(value = "成果ID/帅才ID")
    private String pid;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type;

    @ApiModelProperty(value = "需求合作:邀请揭榜的时候，选择榜单时候传的榜单ID，可以多个")
    private Integer billboardId;

    @ApiModelProperty(value = "合作方式，用逗号分开")
    private String mode;

    @ApiModelProperty(value = "详细说明")
    private String detail;

    @ApiModelProperty(value = "合作方发起人ID", hidden = true)
    private Long launchUserId;

    @ApiModelProperty(value = "合作方发起人名称", hidden = true)
    private String launchUserName;

    @ApiModelProperty(value = "状态：0 待沟通  ...", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "合作发起时间", hidden = true)
    private Date effectAt;

    @ApiModelProperty(value = "成果拥有者ID或者需求时填帅才ID")
    private Long harvestUserId;

    @ApiModelProperty(value = "技术/产品方向，多个用逗号分开")
    private String techKeys;


}
