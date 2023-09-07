package com.gxa.jbgsw.user.entity;

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
 * 
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父ID")
    private Long pid;

    @ApiModelProperty(value = "菜单地址")
    private String url;

    @ApiModelProperty(value = "菜单排序")
    private Integer showInx;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否删除： 0 没有 1 删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long craeteBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "平台：0 后台 1 APP")
    private Integer platform;

    @ApiModelProperty(value = "功能类型： 0 按钮 1 菜单")
    private Integer type;

    @ApiModelProperty(value = "菜单/按钮图形")
    private String icon;

    @ApiModelProperty(value = "权限标识")
    private String code;

    @ApiModelProperty(value = "前端组件")
    private String component;
}
