package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业信息
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_company")
@ApiModel(value="Company对象", description="企业信息")
public class Company implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "logo图片")
    private String logo;

    @ApiModelProperty(value = "所属行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "产品标签")
    private String productLabel;

    @ApiModelProperty(value = "业及技术标签")
    private String techLabel;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "企业类型名称：字典中获取")
    private Integer type;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "发布榜单")
    private Integer publishNum;

    @ApiModelProperty(value = "参与揭榜")
    private Integer partakeNum;

    @ApiModelProperty(value = "拥有成果")
    private Integer harvestNum;

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

    @ApiModelProperty(value = "星级： 1，2，3，4，5")
    private Integer star;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;


}
