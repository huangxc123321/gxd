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
 * 榜单信息
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_billboard")
@ApiModel(value="Billboard对象", description="榜单信息")
public class Billboard implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单发布单位")
    private String unitName;

    @ApiModelProperty(value = "榜单发布单位图标")
    private String unitLogo;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "查询关键字： 榜单标题+工信大类+技术关键词")
    private String queryKeys;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "详情")
    private String content;

    @ApiModelProperty(value = "视频URL地址")
    private String videoUrl;

    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

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

    @ApiModelProperty(value = "排序")
    private Integer seqNo;

    @ApiModelProperty(value = "收藏数")
    private Integer collectNum;

    @ApiModelProperty(value = "分享次数")
    private Integer shareNum;

    @ApiModelProperty(value = "是否置顶： 0 不置顶 1 置顶")
    private Integer isTop;

    @ApiModelProperty(value = "发榜状态：0 待揭榜、1 已签约、2 解决中、3 已解决")
    private Integer status;

    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "发布人")
    private Long createBy;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "审核状态： 0 待审核  1 审核通过  2 审核不通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;

    @ApiModelProperty(value = "审核时间")
    private Long auditCreateAt;
}
