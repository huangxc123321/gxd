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
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_leave_words")
@ApiModel(value="LeaveWords对象", description="留言信息")
public class LeaveWords implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "留言人账号")
    private String code;

    @ApiModelProperty(value = "留言内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "是否回复")
    private Integer reply;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "回复时间")
    private Date updateAt;

    @ApiModelProperty(value = "回复人ID")
    private Long updateBy;


}
