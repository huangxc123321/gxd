package com.gxa.jbgsw.user.entity;

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
 * 
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "密码（md5）加密")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String nick;

    @ApiModelProperty(value = "性别： 0 男 1 女")
    private Integer sex;

    @ApiModelProperty(value = "使用状态： 0 未使用 1 使用")
    private Integer useStauts;

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

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "单位性质： 0 政府部门 1 企业 2 科研机构/团队 3大学院校 4 个人")
    private Integer unitNature;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "单位LOGO")
    private String unitLogo;

    @ApiModelProperty(value = "职称")
    private String job;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "经营范围")
    private String scopeBusiness;

    @ApiModelProperty(value = "企业类型： 字典中获取， typeCode: enterprise_type")
    private Integer buzType;

    @ApiModelProperty(value = "行业: 字典中获取")
    private Integer tradeType;

    @ApiModelProperty(value = "单位简介")
    private String remark;

    @ApiModelProperty(value = "个人类型: 1 经纪人 2 帅才 0 其它")
    private Integer type ;


}
