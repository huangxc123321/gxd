package com.gxa.jbgsw.basis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_technical_field_classify")
@ApiModel(value="TechnicalFieldClassify对象", description="技术领域分类表")
public class TechnicalFieldClassify implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "父级技术领域分类id：如果没有分类默认为-1")
    private String pid;

    @ApiModelProperty(value = "技术领域分类名称")
    private String name;
}
