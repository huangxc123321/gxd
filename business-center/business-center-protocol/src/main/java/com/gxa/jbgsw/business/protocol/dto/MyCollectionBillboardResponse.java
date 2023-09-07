package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MyCollectionBillboardResponse implements Serializable {

    @ApiModelProperty(value = "收藏ID")
    private Long id;

    @ApiModelProperty(value = "相关收藏ID")
    private Long pid;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @ApiModelProperty(value = "状态：待揭榜、已签约、解决中、已解决 (从字典中获取)", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "榜单发布单位")
    private String unitName;

    @ApiModelProperty(value = "榜单发布单位Logo")
    private String unitLogo;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间", hidden = true)
    private Date createAt;

}
