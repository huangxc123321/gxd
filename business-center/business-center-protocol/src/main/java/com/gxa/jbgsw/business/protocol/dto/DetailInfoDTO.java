package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangxc
 */
@Data
public class DetailInfoDTO implements Serializable {
    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "发榜单位")
    private String unitName;

    @ApiModelProperty(value = "榜单发布单位图标")
    private String unitLogo;

        @ApiModelProperty(value = "发榜状态：待揭榜 1，攻关中 2，已完成 3")
    private Integer status;

    @ApiModelProperty(value = "发榜状态名称：待揭榜、已签约、解决中、已解决")
    private String statusName;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

    @ApiModelProperty(value = "发布人")
    private Long createBy;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "工信大类名称")
    private String categoriesName;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
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

    @ApiModelProperty(value = "收藏状态： 0 已收藏  1 未收藏")
    private Integer collectionStatus = 1;

    @ApiModelProperty(value = "发榜人")
    private String publishPerson;

    @ApiModelProperty(value = "是否揭榜： true 已揭榜  false 未揭榜 ")
    private boolean isGain = false;

    @ApiModelProperty(value = "pv")
    private Integer pv = 0;

    @ApiModelProperty(value = "揭榜单位详情")
    List<BillboardGainDTO> billboardGains = new ArrayList<>();

    @ApiModelProperty(value = "成果推荐")
    List<BillboardHarvestRelatedResponse> harvestRecommends = new ArrayList<>();

    @ApiModelProperty(value = "帅才推荐")
    List<BillboardTalentRelatedResponse> talentRecommends = new ArrayList<>();

    @ApiModelProperty(value = "技术经纪人推荐： admin")
    List<BillboardEconomicRelatedResponse> techBrokerRecommends = new ArrayList<>();

    @ApiModelProperty(value = "技术经纪人推荐: web")
    BillboardEconomicRelatedResponse appTechBrokerRecommends = null;

    @ApiModelProperty(value = "我的技术经纪人")
    MyBillboardEconomicManDTO myBillboardEconomicMan;
}
