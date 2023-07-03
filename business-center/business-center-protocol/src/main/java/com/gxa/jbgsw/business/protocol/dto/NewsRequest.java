package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangxc
 */
@Data
public class NewsRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态")
    private Integer type;

    @ApiModelProperty(value = "发榜开始日期")
    private String startDate;

    @ApiModelProperty(value = "发榜结束日期")
    private String endDate;

}
