package com.gxa.jbgsw.basis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_dictionary")
@ApiModel(value="Dictionary对象", description="")
public class Dictionary implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "字典类型ID")
    private Long typeId;

    @ApiModelProperty(value = "字典代码")
    @TableField("dicCode")
    private String dicCode;

    @ApiModelProperty(value = "字典值")
    @TableField("dicValue")
    private String dicValue;

    @ApiModelProperty(value = "排序")
    private Integer showInx;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态：0正常 1 删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;


}
