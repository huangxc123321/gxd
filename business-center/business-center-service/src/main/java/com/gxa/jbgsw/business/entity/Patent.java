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
 * 专利
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_patent")
@ApiModel(value="Patent对象", description="专利")
public class Patent implements Serializable {

private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "成果ID")
    private Long pid;

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

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;


}
