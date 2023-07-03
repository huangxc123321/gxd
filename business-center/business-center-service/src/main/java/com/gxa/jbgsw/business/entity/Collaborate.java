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
 * 我的合作
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_collaborate")
@ApiModel(value="Collaborate对象", description="我的合作")
public class Collaborate implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type;

    @ApiModelProperty(value = "发起方")
    private Long launchUserId;

    private Long pid;

    @ApiModelProperty(value = "成果拥有者ID")
    private Long harvestUserId;

    @ApiModelProperty(value = "状态：0 待回复  ...")
    private Integer status;

    @ApiModelProperty(value = "合作方式")
    private String mode;

    @ApiModelProperty(value = "详情")
    private String detail;

    @ApiModelProperty(value = "合作发起时间")
    private Date effectAt;

    private String tech;


}
