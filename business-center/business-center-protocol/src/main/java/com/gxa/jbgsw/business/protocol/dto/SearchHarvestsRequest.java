package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchHarvestsRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

    @ApiModelProperty(value = "行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "技术领域：字典中获取")
    private String techDomain;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;


}