package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangxc
 */
@Data
public class CompanyResponse implements Serializable {


    @ApiModelProperty(value = "企业ID")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "logo图片")
    private String logo;

    @ApiModelProperty(value = "所属行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "所属行业名称：字典中获取")
    private String tradeTypeName;

    @ApiModelProperty(value = "产品标签")
    private String productLabel;

    @ApiModelProperty(value = "业及技术标签")
    private String techLabel;

    @ApiModelProperty(value = "企业统一社会信用代码")
    private String code;

    @ApiModelProperty(value = "单位负责人")
    private String director;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "公司网址")
    private String url;

    @ApiModelProperty(value = "公司简介")
    private String remark;

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "企业类型名称：字典中获取")
    private Integer type;

    @ApiModelProperty(value = "企业类型名称")
    private String typeName;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "省ID")
    private Long provinceId;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "发布榜单")
    private Integer publishNum;

    @ApiModelProperty(value = "参与揭榜")
    private Integer partakeNum;

    @ApiModelProperty(value = "拥有成果")
    private Integer harvestNum;

    @ApiModelProperty(value = "单位性质： 0 政府部门 1 企业 2 科研机构/团队 3大学院校 4 个人")
    private Integer unitNature;

    @ApiModelProperty(value = "单位性质： 0 政府部门 1 企业 2 科研机构/团队 3大学院校 4 个人")
    private String unitNatureName;
}
