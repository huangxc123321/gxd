package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyHavestBillboardResponse implements Serializable {

    @ApiModelProperty(value = "成果ID")
    private Long id;

    @ApiModelProperty(value = "相关收藏ID")
    private Long pid;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "榜单发布单位Logo")
    private String unitLogo;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "成果简介")
    private String remark;

}
