package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 搜索团队信息的DTO
 */
@Data
public class SearchTeamsResponse implements Serializable {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "logo图片")
    private String logo;

    @ApiModelProperty(value = "所属行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "所属行业名称")
    private String tradeTypeName;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "产品标签")
    private String productLabel;

    @ApiModelProperty(value = "行业及技术标签")
    private String techLabel;


}
