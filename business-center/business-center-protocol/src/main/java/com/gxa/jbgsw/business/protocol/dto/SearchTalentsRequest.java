package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchTalentsRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

    @ApiModelProperty(value = "技术领域：字典获取（tech_domain）")
    private String techDomain;

    @ApiModelProperty(value = "学历：字典获取(highest_edu)")
    private String highestEdu;

    @ApiModelProperty(value = "性别： 0 男 1女")
    private Integer sex;

}
