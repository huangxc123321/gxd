package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchEconomicMansRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

    @ApiModelProperty(value = "技术领域：字典获取（tech_domain）")
    private String techDomain;

    @ApiModelProperty(value = "类型：字典中获取比如（国内高校在职科研人员 国内高校在读博士    非高校在读博士    非高校在职科研人员）")
    private String type;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Integer level;

}
