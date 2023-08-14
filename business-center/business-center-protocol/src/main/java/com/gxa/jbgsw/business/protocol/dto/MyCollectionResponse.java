package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyCollectionResponse implements Serializable {

    // 收藏类型： 0政府榜 1企业榜 2成果 3政策 4帅才

    @ApiModelProperty(value = "政府榜数量")
    Integer govBillboardsNum = 0;

    @ApiModelProperty(value = "政府榜列表数据")
    List<MyCollectionBillboardResponse> govBillboards;

    @ApiModelProperty(value = "企业榜数量")
    Integer buzBillboardsNum = 0;

    @ApiModelProperty(value = "企业榜列表数据")
    List<MyCollectionBillboardResponse> buzBillboards;

    @ApiModelProperty(value = "成果榜数量")
    Integer havestBillboardsNum = 0;

    @ApiModelProperty(value = "成果榜列表数据")
    List<MyHavestBillboardResponse> havestBillboards;

    @ApiModelProperty(value = "政策数量")
    Integer policyNum = 0;

    @ApiModelProperty(value = "政策列表数据")
    List<MypolicyResponse> policys;


    @ApiModelProperty(notes = "页数（默认1）")
    private Integer pageNum;

    @ApiModelProperty(notes = "纪录数（默认10）")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("当前页的记录数量")
    private int size;

    @ApiModelProperty("总页数")
    private int pages;
}
