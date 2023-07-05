package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BannerResponse implements Serializable {

    @ApiModelProperty(value = "主键: 新增时不填写为null, 更新的时候需要用填写ID，不能为空")
    private Long id;

    @ApiModelProperty(value = "类型： 0 pc , 1 app")
    private Integer type;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer seqNo;

    @ApiModelProperty(value = "图片链接地址")
    private String links;

    @ApiModelProperty(value = "生效时间")
    private Date effectAt;

}