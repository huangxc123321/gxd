package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 帅才库
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_talent_pool")
@ApiModel(value="TalentPool对象", description="帅才库")
public class TalentPool implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobie;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "毕业院校")
    private String graduateSchool;

    @ApiModelProperty(value = "状态：0 正常， 1-注销")
    private Integer status;

    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;

    @ApiModelProperty(value = "审核时间")
    private Date auditDate;

    @ApiModelProperty(value = "最高学历: 字典中获取")
    private String highestEdu;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "个人简介")
    private String remark;

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

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "职称")
    private Long professional;

    @ApiModelProperty(value = "研究方向")
    private String researchDirection;

    @ApiModelProperty(value = "论文（JSON串，题目跟论文地址）")
    private String treatise;

    @ApiModelProperty(value = "项目")
    private String project;

    @ApiModelProperty(value = "简历")
    private String curriculumVitae;

    @ApiModelProperty(value = "性别： 0 男 1女")
    private Long sex;

    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "记录更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "审核是否通过原因")
    private String auditReason;

    @ApiModelProperty(value = "审核是否通过原因")
    private String auditUserName;

    @ApiModelProperty(value = "查询关键字： 职称+工信大类+技术关键词")
    private String queryKeys;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

}
