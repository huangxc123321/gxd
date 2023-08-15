package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_news")
@ApiModel(value="News对象", description="")
public class News implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "新闻或者政策新闻： 0 新闻 1 政策")
    private Integer newsPolicy;

    @ApiModelProperty(value = "新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态")
    private Integer type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "从内容获取的展示图片")
    private String picture;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "地区范围")
    private String regionScope;

    @ApiModelProperty(value = "来源于")
    private String origin;

    @ApiModelProperty(value = "发布时间")
    private Date publishAt;

    @ApiModelProperty(value = "是否定时发布 0 不定时  1定时")
    private Integer isFixed;

    @ApiModelProperty(value = "定时发布时间")
    private Date fixedAt;

    @ApiModelProperty(value = "状态：0 发布， 1 待发布")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "更新者")
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "预览量")
    private Integer views;

    @ApiModelProperty(value = "发布者名称")
    private String createName;

}
