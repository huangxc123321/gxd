package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 成果信息
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_harvest")
@ApiModel(value="Harvest对象", description="成果信息")
public class Harvest implements Serializable {

private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "成果名称")
    private String name;

    @ApiModelProperty(value = "行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "成熟度：字典中获取")
    private Integer maturityLevel;

    @ApiModelProperty(value = "应用领域")
    private String appyDomain;

    @ApiModelProperty(value = "成果简介")
    private String remark;

    @ApiModelProperty(value = "是否专利，0 不是 1是")
    private Integer isPatent;

    @ApiModelProperty(value = "关联专利，多个专利用逗号分开")
    private String relatedPatent;

    @ApiModelProperty(value = "技术持有人")
    private String holder;

    @ApiModelProperty(value = "所属单位")
    private String unitName;

    @ApiModelProperty(value = "联系人")
    private String contacts;

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

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "展示图：从详情中获取第一张图片，记录下来")
    private String guidePicture;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "查询关键字")
    private String queryKeys;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

}
