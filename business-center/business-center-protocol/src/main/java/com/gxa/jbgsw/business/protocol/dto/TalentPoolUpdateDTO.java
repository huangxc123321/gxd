package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TalentPoolUpdateDTO implements Serializable {

    @ApiModelProperty(value = "用户ID: 新增的時候传null, 编辑传用户ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobie;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "出生年月")
    private Date birthday;

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

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "毕业院校")
    private String graduateSchool;

    @ApiModelProperty(value = "最高学历: 字典中获取")
    private String highestEdu;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "职称")
    private Long professional;

    @ApiModelProperty(value = "个人简介")
    private String remark;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

    @ApiModelProperty(value = "论文（JSON串，题目跟论文地址）")
    private String treatise;

    @ApiModelProperty(value = "项目")
    private String project;

    @ApiModelProperty(value = "简历")
    private String curriculumVitae;

    @ApiModelProperty(value = "照片")
    private String photo;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updateBy;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateAt;

}
