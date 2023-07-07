package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyAttentionRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "关注类型：0 政府部门， 1 企业  2 帅才")
    private Integer type;

}
