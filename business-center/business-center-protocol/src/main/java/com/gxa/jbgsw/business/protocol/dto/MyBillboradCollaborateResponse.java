package com.gxa.jbgsw.business.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.naming.directory.SearchResult;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 邀请揭榜：返回的列表项内容
 */
@Data
public class MyBillboradCollaborateResponse implements Serializable {

    @ApiModelProperty(value = "榜单ID")
    private Long id;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "技术关键词，用逗号分隔")
    private String techKeys;

    @ApiModelProperty(value = "系统推荐匹配度")
    private Double sStar;

}
