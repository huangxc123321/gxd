package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huangxc
 */
@Data
public class BillboardResponse implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "发榜状态：待揭榜 1，攻关中 2，已完成 3")
    private Integer status;

    @ApiModelProperty(value = "发榜状态名称：待揭榜 1，攻关中 2，已完成 3")
    private String statusName;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "发榜单位")
    private String unitName;

    @ApiModelProperty(value = "榜单发布单位图标")
    private String unitLogo;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类名称")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "发布人")
    private Long createBy;

    @ApiModelProperty(value = "发布人(认证了显示认证信息)")
    private String createByName;

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

    @ApiModelProperty(value = "收藏数")
    private Integer collectNum;

    @ApiModelProperty(value = "分享数")
    private Integer shareNum;

    @ApiModelProperty(value = "排序")
    private Integer seqNo;

    @ApiModelProperty(value = "是否置顶： 0 不置顶 1 置顶")
    private Integer isTop;

    @ApiModelProperty(value = "审核状态： 0 待审核  1 审核通过  2 审核不通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;

    @ApiModelProperty(value = "审核人名称")
    private String auditUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "审核时间")
    private Date auditCreateAt;

    @ApiModelProperty(value = "发榜人")
    private String publishPerson;

    @ApiModelProperty(value = "是否创建视频： true 创建， false 不创建", hidden = true)
    private Integer isCreateVideo;

    @ApiModelProperty(value = "是否创建视频： true 创建， false 不创建")
    private boolean createVideo = false;

    @ApiModelProperty(value = "pv")
    private Integer pv;


}
