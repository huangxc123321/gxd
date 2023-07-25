package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SearchBizRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

    @ApiModelProperty(value = "发榜状态：0 待揭榜、1 已签约、2 解决中、3 已解决")
    private Integer status;

    @ApiModelProperty(value = "发榜金额: 最低金额")
    private BigDecimal startAmount;

    @ApiModelProperty(value = "发榜金额: 最高金额， 最高金额为0 表示面议")
    private BigDecimal endtAmount;

    @ApiModelProperty(value = "发榜开始日期")
    private String startDate;

    @ApiModelProperty(value = "发榜结束日期")
    private String endDate;

    @ApiModelProperty(value = "工信大类：字典中获取")
    private Integer categories;

    @ApiModelProperty(value = "企业类型：字典中获取")
    private Integer companyType;

    @ApiModelProperty(value = "星级指数")
    private Integer star;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜", hidden = true)
    private Integer type;
}
