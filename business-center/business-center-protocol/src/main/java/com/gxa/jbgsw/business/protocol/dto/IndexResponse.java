package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexResponse implements Serializable {
    @ApiModelProperty(value = "最新榜单")
    private List<BillboardIndexDTO> lasts;

    @ApiModelProperty(value = "政府榜")
    private List<BillboardIndexDTO> govs;

    @ApiModelProperty(value = "企业榜")
    private List<BillboardIndexDTO> bizs;

}
