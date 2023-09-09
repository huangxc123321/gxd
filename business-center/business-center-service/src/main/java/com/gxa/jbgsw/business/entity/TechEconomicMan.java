package com.gxa.jbgsw.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 技术经济人
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_tech_economic_man")
@ApiModel(value="TechEconomicMan对象", description="技术经济人")
public class TechEconomicMan implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "专业标签，多个用英文逗号分开(生物与新医药、新材料、保鲜技术、智能终端、IC芯片技术、纳米技术、精细化工)")
    private String label;

    @ApiModelProperty(value = "等级：0 无， 1 金  2 银  3 铜")
    private Integer level;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "经纪人类型：字典中获取")
    private Integer type;

    @ApiModelProperty(value = "邮箱")
    private String email;

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

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "发布人")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "状态： 0 正常， 1 暂没发布")
    private Integer status;

    @ApiModelProperty(value = "分数 ")
    private BigDecimal score;

    @ApiModelProperty(value = "人物照片")
    private String avatar;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "协议地址")
    private String agreements;

    @ApiModelProperty(value = "协议显示名称")
    private String agreementsName;

    @ApiModelProperty(value = "需求派单：接单次数,系统向经纪人派单的次数")
    private Integer requires;

    @ApiModelProperty(value = "关注数: 有多少人关注了这个经纪人")
    private Integer attentions;

    @ApiModelProperty(value = "总促成:是指完成项目的总数量，完成项目以评价完成为准")
    private Integer successTotal;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;



}
