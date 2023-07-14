package com.gxa.jbgsw.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_share_comment")
@ApiModel(value="ShareComment对象", description="分享视频或文章的评论")
public class ShareComment implements Serializable {


    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "分享文章或视频ID")
    private Long shareId;

    @ApiModelProperty(value = "父级评论id：如果没有评论默认为-1")
    private String parentId;

    @ApiModelProperty(value = "评论人ID")
    private Long userId;

    @ApiModelProperty(value = "评论人像")
    private String avatar;

    @ApiModelProperty(value = "评论人昵称")
    private String nick;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "发布时间")
    private Date createAt;

}
