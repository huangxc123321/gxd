package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CollectionDTO implements Serializable {

    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "收藏状态： 0 收藏， 1 取消")
    private Integer status;

    @ApiModelProperty(value = "收藏类型： 0政府榜 1企业榜 2成果 3政策 4帅才")
    private Integer collectionType;

    @ApiModelProperty(value = "收藏关联的ID")
    private Long pid;

    @ApiModelProperty(value = "收藏时间", hidden = true)
    private Date createAt;

}
