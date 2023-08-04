package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LeaveWordsDTO implements Serializable {

    @ApiModelProperty(value = "主键: 新增时不填写为null, 更新的时候需要用填写ID，不能为空")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "留言人账号", hidden = true)
    private String code;

    @ApiModelProperty(value = "留言内容")
    private String content;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "回复时间", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "回复人ID", hidden = true)
    private Long updateBy;
}
