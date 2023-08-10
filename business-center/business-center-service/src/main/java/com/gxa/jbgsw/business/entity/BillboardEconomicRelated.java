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
 * 榜单与经纪人的匹配关联表
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_billboard_economic_related")
@ApiModel(value="BillboardEconomicRelated对象", description="榜单与经纪人的匹配关联表")
public class BillboardEconomicRelated implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "榜单ID")
    private Long billboardId;

    @ApiModelProperty(value = "经纪人用户ID")
    private Long economicUserId;

    @ApiModelProperty(value = "经纪人ID")
    private Long economicId;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double sStar;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Double hStart;

    @ApiModelProperty(value = "记录创建时间")
    private Date createAt;

    @ApiModelProperty(value = "手工推荐人ID(user_id)")
    private Long userId;

    @ApiModelProperty(value = "推荐人名称（冗余，便于以后查询）")
    private String userName;

    @ApiModelProperty(value = "推荐时间")
    private Date recommendAt;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态： 0 待派单， 1推荐，2 已接受 , 3 已拒绝")
    private  Integer status;

}
