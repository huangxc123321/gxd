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

}
