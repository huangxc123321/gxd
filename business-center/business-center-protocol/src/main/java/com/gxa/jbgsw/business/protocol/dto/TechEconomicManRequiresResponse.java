package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 经纪人的需求对象
 */
@Data
public class TechEconomicManRequiresResponse  implements Serializable {

    @ApiModelProperty(value = "需求派单ID")
    private Long id;

    @ApiModelProperty(value = "榜单ID")
    private Long billboardId;

    @ApiModelProperty(value = "状态： 0 待派单， 1推荐，2 已接受 , 3 已拒绝")
    private  Integer status;

    @ApiModelProperty(value = "状态名称")
    private  String statusName;

    @ApiModelProperty(value = "榜单名称")
    private String title;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "发榜单位LOGO： 冗余字段，便于以后查询显示需要")
    private String unitLogo;

    @ApiModelProperty(value = "有效开始时间")
    private Date startAt;

    @ApiModelProperty(value = "有效结束时间")
    private Date endAt;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Double hstar;

    @ApiModelProperty(value = "不推荐理由")
    private String remark;

    @ApiModelProperty(value = "相关成果: 名称用逗号分隔")
    private String havests;

    @ApiModelProperty(value = "相关帅才： 名称用逗号分隔")
    private String talents;

}
