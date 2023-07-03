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

    private String title;

    private String content;

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过")
    private Integer status;

    private Date createAt;

    private Long createBy;

    @ApiModelProperty(value = "视频链接地址")
    private String links;

    private String from;

    private Integer views;

    private Integer likes;

    private Integer comments;


}
