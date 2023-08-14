package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyCollectionRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "收藏类型： 0政府榜 1企业榜 2成果 3政策 ")
    private Integer collectionType;

}
