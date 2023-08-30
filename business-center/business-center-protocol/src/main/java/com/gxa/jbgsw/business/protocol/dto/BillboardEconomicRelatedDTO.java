package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardEconomicRelatedDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "榜单ID")
    private Long billboardId;

    @ApiModelProperty(value = "经纪人ID")
    private Long economicId;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double star;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Double hStart;

    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "手工推荐人ID(user_id)")
    private Long userId;

    @ApiModelProperty(value = "推荐人名称（冗余，便于以后查询）")
    private String userName;

    @ApiModelProperty(value = "推荐时间")
    private Date recommendAt;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态： 0 待派单， 1推荐，2 已接受 , 3 已拒绝")
    private  Integer status;


}
