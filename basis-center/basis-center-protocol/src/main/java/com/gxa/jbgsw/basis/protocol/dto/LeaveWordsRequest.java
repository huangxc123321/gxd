package com.gxa.jbgsw.basis.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LeaveWordsRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "是否回复： 0 待回复 ，1 已回复")
    private Integer reply;

    @ApiModelProperty(value = "留言开始日期")
    private String startDate;

    @ApiModelProperty(value = "留言结束日期")
    private String endDate;

}
