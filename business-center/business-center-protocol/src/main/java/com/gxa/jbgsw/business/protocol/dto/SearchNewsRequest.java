package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchNewsRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "新闻或者政策新闻： 0 新闻 1 政策")
    private Integer type = 0;

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份市地区的中文名称： 后台发表的时候是输入中文的，所以需要中文")
    private String address;

    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

}
