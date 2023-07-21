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

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "发榜单位")
    private String unitName;

    @ApiModelProperty(value = "发榜状态：0 待揭榜、1 已签约、2 解决中、3 已解决")
    private Integer status;

    @ApiModelProperty(value = "发榜状态名称：待揭榜、已签约、解决中、已解决")
    private String statusName;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

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

    @ApiModelProperty(value = "揭榜单位详情")
    List<BillboardGainDTO> billboardGains = new ArrayList<>();

    @ApiModelProperty(value = "成果推荐")
    List<BillboardHarvestRelatedResponse> harvestRecommends = new ArrayList<>();

    @ApiModelProperty(value = "帅才推荐")
    List<BillboardTalentRelatedResponse> talentRecommends = new ArrayList<>();

    @ApiModelProperty(value = "技术经纪人推荐")
    List<BillboardEconomicRelatedResponse> techBrokerRecommends = new ArrayList<>();
}
