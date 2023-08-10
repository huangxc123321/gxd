package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_billboard_lifecycle")
@ApiModel(value="Billboardlifecycle对象", description="跟进对象")
public class Billboardlifecycle implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "派单ID")
    private Long pid;

    @ApiModelProperty(value = "跟进描述")
    private String content;

    @ApiModelProperty(value = "状态： 0 未结束  1 结束")
    private Integer status;

    @ApiModelProperty(value = "跟进时间")
    private Date createAt;

    @ApiModelProperty(value = "跟进人")
    private Long createBy;

    @ApiModelProperty(value = "跟进人姓名")
    private String userName;

    @ApiModelProperty(value = "附件，多个附件用 逗号分隔")
    private String attachments;

}
