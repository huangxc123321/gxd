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
@TableName("t_message")
@ApiModel(value="Message对象", description="消息")
public class Message implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "消息类型：0 系统消息  1 技术咨询")
    private Integer type;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息关联第三方ID")
    private Long pid;

    @ApiModelProperty(value = "系统消息来源：0 揭榜申请 1 榜单推荐 2 合作发起")
    private Integer origin;

    @ApiModelProperty(value = "读标志： 0 未读  1 已读")
    private Integer readFlag;

    @ApiModelProperty(value = "消息生成时间")
    private Date createAt;

}
