package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author huangxc
 */
@Data
public class CompanyDTO implements Serializable {

    @ApiModelProperty(value = "企业ID: 新增的時候传null, 编辑传企业ID")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "logo图片")
    private String logo;

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

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String director;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "所属行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "企业类型：字典中获取")
    private Integer type;

    @ApiModelProperty(value = "企业统一社会信用代码")
    private String code;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "公司网址")
    private String url;

    @ApiModelProperty(value = "公司简介")
    private String remark;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "多个请用分号 ; 隔开")
    private String productLabel;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updateBy;

}
