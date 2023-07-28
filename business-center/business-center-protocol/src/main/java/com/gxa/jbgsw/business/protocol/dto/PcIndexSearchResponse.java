package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PcIndexSearchResponse implements Serializable {
    @ApiModelProperty(value = "政府榜数量")
    long govBillboradsNum;

    @ApiModelProperty(value = "企业榜数量")
    long bizBillboradsNum;

    @ApiModelProperty(value = "成果数量")
    long havestsNum;

    @ApiModelProperty(value = "帅才数量")
    long talentsNum;

    @ApiModelProperty(value = "团队数量")
    long teamsNum;

    @ApiModelProperty(value = "政策数量")
    long policyNum;

    @ApiModelProperty(value = "政府榜列表")
    List<BillboardIndexDTO> govBillborads;

    @ApiModelProperty(value = "企业榜列表")
    List<BillboardIndexDTO> bizBillborads;

    @ApiModelProperty(value = "成果列表")
    List<SearchHavestResponse> havests;

    @ApiModelProperty(value = "帅才列表")
    List<TalentPoolResponse> talents;

    @ApiModelProperty(value = "团队列表")
    List<SearchTeamsResponse> teams;

    @ApiModelProperty(value = "政策列表")
    List<SearchNewsResponse> policys;


}
