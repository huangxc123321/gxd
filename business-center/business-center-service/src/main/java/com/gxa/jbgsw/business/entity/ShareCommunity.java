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
 * 分享社区
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_share_community")
@ApiModel(value="ShareCommunity对象", description="分享社区")
public class ShareCommunity implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "类型：0 文章  1 视频")
    private Integer type;

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享内容")
    private String content;

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过")
    private Integer status;

    @ApiModelProperty(value = "分享时间")
    private Date createAt;

    @ApiModelProperty(value = "分享人ID")
    private Long createBy;

    @ApiModelProperty(value = "分享人昵称")
    private String nick;

    @ApiModelProperty(value = "视频链接地址")
    private String links;

    @ApiModelProperty(value = "来自哪里")
    private String from;

    @ApiModelProperty(value = "预览数")
    private Integer views;

    @ApiModelProperty(value = "点赞数")
    private Integer likes;

    @ApiModelProperty(value = "评论数")
    private Integer comments;


}
