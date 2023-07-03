package com.gxa.jbgsw.business.protocol.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangxc
 */
@Data
public class NewsDTO implements Serializable {


    @ApiModelProperty(value = "新闻ID: 新增的時候传null, 编辑传ID")
    private Long id;

    @ApiModelProperty(value = "新闻或者政策新闻： 0 新闻 1 政策")
    private Integer newsPolicy;

    @ApiModelProperty(value = "新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态")
    private Integer type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "地区范围")
    private String regionScope;

    @ApiModelProperty(value = "来源于")
    private String from;

    @ApiModelProperty(value = "发布时间")
    private Date publishAt;

    @ApiModelProperty(value = "是否定时发布 0 不定时  1定时")
    private Integer isFixed;

    @ApiModelProperty(value = "定时发布时间")
    private Date fixedAt;

    @ApiModelProperty(value = "状态：0 发布， 1 待发布", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新者", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updateBy;

    @ApiModelProperty(value = "发布者名称： 显示的时候，获取发布者名称")
    private String createName;

}
