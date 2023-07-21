package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillboardRelatedDTO implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long billboardId;

    @ApiModelProperty(value = "成果ID")
    private Long harvestId;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Integer sStar;

    @ApiModelProperty(value = "人工手动推荐匹配度")
    private Integer hStart;

    @ApiModelProperty(value = "记录创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "手工推荐人ID(user_id)", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "推荐人名称（冗余，便于以后查询）", hidden = true)
    private String userName;

    @ApiModelProperty(value = "推荐时间", hidden = true)
    private Date recommendAt;

    @ApiModelProperty(value = "推荐状态： 0 不推荐 1 推荐")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
