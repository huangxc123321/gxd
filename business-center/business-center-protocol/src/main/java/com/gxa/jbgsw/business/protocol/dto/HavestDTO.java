package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 成果新增，更新用
 */
@Data
public class HavestDTO implements Serializable {
    @ApiModelProperty(value = "成果ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "应用领域(手工输入)：用 “；” 分开")
    private String appyDomain;

    @ApiModelProperty(value = "成果简介")
    private String remark;

    @ApiModelProperty(value = "所属机构")
    private String unitName;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "是否专利，0 不是 1是")
    private Integer isPatent;

    @ApiModelProperty(value = "关联专利")
    private List<PatentDTO> patents;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;


    /**
     *  下面暂时不用
     */

    @ApiModelProperty(value = "行业领域：字典中获取(废弃)")
    private String tradeType;

    @ApiModelProperty(value = "技术持有人")
    private String holder;

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

}
