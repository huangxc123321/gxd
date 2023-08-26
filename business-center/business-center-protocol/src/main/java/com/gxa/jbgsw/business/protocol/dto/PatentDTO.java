package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Mr. huang
 * @Date 2023/7/4 0004 9:51
 * @Version 2.0
 */
@Data
public class PatentDTO implements Serializable {

    @ApiModelProperty(value = "专利ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "专利号")
    private String no;

    @ApiModelProperty(value = "专利名称")
    private String name;

    @ApiModelProperty(value = "专利申请时间")
    private Date applyDate;

    @ApiModelProperty(value = "省ID")
    private Long provinceId;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "专利所属地区")
    private Integer areaId;

    @ApiModelProperty(value = "专利类型: 0 发明专利, 1 实用新型专利, 2 外观设计专利")
    private String type;

    @ApiModelProperty(value = "专利授权时间")
    private Date authorizeDate;

    @ApiModelProperty(value = "专利摘要")
    private String summary;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

}
