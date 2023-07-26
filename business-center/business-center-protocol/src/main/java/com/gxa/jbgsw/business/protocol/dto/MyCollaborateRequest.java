package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyCollaborateRequest implements Serializable {

    @ApiModelProperty(value = "发起方： 0 我发起， 1 我收到")
    private Integer initiate ;

    @ApiModelProperty(value = "合作类型：0 成果合作  1 需求合作")
    private Integer type ;

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;


}
