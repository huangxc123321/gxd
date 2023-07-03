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

    private Long id;

    @ApiModelProperty(value = "专利号")
    private String no;

    private String name;

    private Date applyDate;

    private Integer areaId;

    private String type;

    private Date authorizeDate;

    @ApiModelProperty(value = "专利摘要")
    private String summary;

    private Date createAt;

    private Long createBy;


}
