package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CollaborateTaleDetailDTO implements Serializable {

    @ApiModelProperty(value = "帅才ID合作ID")
    private Long id;

    @ApiModelProperty(value = "帅才ID")
    private Long pid;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "职称中文名称")
    private String professionalName;

    @ApiModelProperty(value = "专业方向")
    private String techDomainName;

    @ApiModelProperty(value = "研究成果")
    private String researchDirection;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type;

    @ApiModelProperty(value = "合作方式，用逗号分开, 名称见字典：collaborate_mode")
    private String  mode;

    @ApiModelProperty(value = "需求合作:邀请揭榜的时候，榜单信息")
    private List<MyBillboradCollaborateResponse> billboards;

    @ApiModelProperty(value = "核心需求")
    private String detail;

    @ApiModelProperty(value = "技术/产品方向，多个用逗号分开")
    private String techKeys;

}
