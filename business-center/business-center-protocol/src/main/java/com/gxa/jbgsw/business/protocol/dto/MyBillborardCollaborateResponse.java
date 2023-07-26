package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 需求合作
 */
@Data
public class MyBillborardCollaborateResponse implements Serializable {

    @ApiModelProperty(value = "成果合作ID")
    private Long id;

    @ApiModelProperty(value = "榜单所有者名称")
    private String name;

    @ApiModelProperty(value = "合作方式，用逗号分开")
    private String mode;

    @ApiModelProperty(value = "合作方式显示名称，用逗号分开")
    private String modeName;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "合作发起时间", hidden = true)
    private Date effectAt;
}
