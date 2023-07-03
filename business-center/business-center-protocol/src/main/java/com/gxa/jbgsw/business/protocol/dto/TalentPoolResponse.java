package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangxc
 */
@Data
public class TalentPoolResponse implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态：0 待审核, 1 已审核, 2未通过")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "职称：字典中获取")
    private Integer professional;

    @ApiModelProperty(value = "职称名称")
    private String professionalName;

    @ApiModelProperty(value = "学历")
    private String highestEdu;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

    @ApiModelProperty(value = "研究成果")
    private String harvest;

    @ApiModelProperty(value = "被关注数")
    private String attentionNum;

    @ApiModelProperty(value = "分享次数")
    private String shareNum;

    @ApiModelProperty(value = "申请时间")
    private Date applyDate;

    @ApiModelProperty(value = "审核人")
    private String auth;

    @ApiModelProperty(value = "审核时间")
    private Date authDate;

}
