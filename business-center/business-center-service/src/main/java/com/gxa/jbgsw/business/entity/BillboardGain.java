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
 * 接榜
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_billboard_gain")
@ApiModel(value="BillboardGain对象", description="接榜")
public class BillboardGain implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "相关榜单ID")
    private Long pid;

    @ApiModelProperty(value = "备注")
    private String remak;

    @ApiModelProperty(value = "揭榜方案：附件")
    private String attachment;

    @ApiModelProperty(value = "揭榜单位")
    private String acceptBillboard;

    @ApiModelProperty(value = "申请时间")
    private Date applyAt;

    @ApiModelProperty(value = "揭榜审核状态：0 审核不通过  1 审核通过")
    private Integer auditingStatus;

    @ApiModelProperty(value = "审核人")
    private String auditingUserId;

    @ApiModelProperty(value = "审核不通过的原因")
    private String reason;

    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;


}
