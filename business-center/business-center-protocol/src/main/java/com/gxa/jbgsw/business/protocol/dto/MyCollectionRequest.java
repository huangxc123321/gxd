package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyCollectionRequest {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "收藏类型： 0政府榜 1企业榜 2成果 3政策 ")
    private Integer collectionType;

}
