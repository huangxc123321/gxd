package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.list.AbstractLinkedList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RelateDTO implements Serializable {

    @ApiModelProperty(value = "相关成果")
    List<RelateHavestDTO> havests = new ArrayList<>();

    @ApiModelProperty(value = "推荐相关帅才")
    List<RelateTalentDTO> talents = new ArrayList<>();

    @ApiModelProperty(value = "推荐相关榜单")
    List<RelateBillboardDTO> billboards = new ArrayList<>();



}
