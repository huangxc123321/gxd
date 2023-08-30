package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 17:35
 * @Version 2.0
 */
@Data
public class HavestDTO implements Serializable {
    @ApiModelProperty(value = "成果ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "行业领域：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "技术领域(第一级)：字典中获取")
    private String techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private String techDomain2;

    @ApiModelProperty(value = "技术领域(第三级，也就是最后)：字典中获取")
    private String techDomain;

    @ApiModelProperty(value = "技术领域显示名称")
    private String techDomainName;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "成熟度名称：字典中获取", hidden = true)
    private String maturityLevelName;

    @ApiModelProperty(value = "应用领域： 手工输入")
    private String appyDomain;

    @ApiModelProperty(value = "成果简介")
    private String remark;

    @ApiModelProperty(value = "是否专利，0 不是 1是")
    private Integer isPatent;

    @ApiModelProperty(value = "关联专利")
    private List<PatentDTO> patents;

    @ApiModelProperty(value = "技术持有人")
    private String holder;

    @ApiModelProperty(value = "所属机构")
    private String unitName;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "技术创新点")
    private String innovationPointUrl;

    @ApiModelProperty(value = "技术专家")
    private String specialistUrl;

    @ApiModelProperty(value = "发展前景")
    private String vistaUrl;

    @ApiModelProperty(value = "预期效益")
    private String benefitUrl;

    @ApiModelProperty(value = "成果案例")
    private String caseUrl;


    @ApiModelProperty(value = "是否合作： true：已合作  false: 未合作")
    private boolean isCollaborate = false;

    @ApiModelProperty(value = "收藏状态： 0 已收藏  1 未收藏")
    private Integer collectionStatus = 1;

    @ApiModelProperty(value = "榜单成果推荐")
    List<BillboardHarvestRelatedResponse> billboardHarvestRecommends = new ArrayList<>();

    @ApiModelProperty(value = "合作状态")
    List<HavestCollaborateDTO> havestCollaborates = new ArrayList<>();

}
