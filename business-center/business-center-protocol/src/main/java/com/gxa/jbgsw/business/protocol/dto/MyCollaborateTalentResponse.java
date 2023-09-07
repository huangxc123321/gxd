package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyCollaborateTalentResponse implements Serializable {

    @ApiModelProperty(value = "合作ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "合作方式，用逗号分开")
    private String mode;

    @ApiModelProperty(value = "合作方式显示名称，用逗号分开")
    private String modeName;

    @ApiModelProperty(value = "榜单")
    private String title;

    @ApiModelProperty(value = "技术方向")
    private String techDomainName;

    @ApiModelProperty(value = "详细说明")
    private String detail;

    @ApiModelProperty(value = "状态：0 待沟通 1 已同意  2 已拒绝")
    private Integer status;

    @ApiModelProperty(value = "状态显示名称：状态：0 待沟通 1 已同意  2 已拒绝 ")
    private String statusName;

    @ApiModelProperty(value = "合作发起时间")
    private String effectAt;

    @ApiModelProperty(value = "需求用户名称")
    private String unitName;

    @ApiModelProperty(value = "需求用户logo图片")
    private String logo;

    @ApiModelProperty(value = "合作方的联系电话")
    private String mobile;

    @ApiModelProperty(value = "合作申请拒绝原因")
    private String remark;
}
