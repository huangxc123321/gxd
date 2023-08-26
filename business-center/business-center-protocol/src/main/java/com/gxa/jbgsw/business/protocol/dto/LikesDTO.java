package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LikesDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "点赞关联的ID")
    private Long pid;

    @ApiModelProperty(value = "点赞时间", hidden = true)
    private Date createAt;

}
