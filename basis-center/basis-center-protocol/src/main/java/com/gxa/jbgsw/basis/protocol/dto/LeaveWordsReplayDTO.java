package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LeaveWordsReplayDTO implements Serializable {

    @ApiModelProperty(value = "主键: 新增时不填写为null, 更新的时候需要用填写ID，不能为空")
    private Long id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "回复时间", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "回复人ID", hidden = true)
    private Long updateBy;

}
