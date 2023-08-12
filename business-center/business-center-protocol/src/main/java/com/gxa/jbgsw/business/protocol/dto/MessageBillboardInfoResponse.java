package com.gxa.jbgsw.business.protocol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 消息中点击需求查看详情
 */
@Data
public class MessageBillboardInfoResponse implements Serializable {

    @ApiModelProperty(value = "需求派单ID(接受操作时，需要该ID)")
    private Long id;

    @ApiModelProperty(value = "榜单标题")
    private String title;

    @ApiModelProperty(value = "技术关键词，用逗号分隔， 直接输入")
    private String techKeys;

    @ApiModelProperty(value = "意向金额：0 面议， 其它为具体金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "需求详情")
    private String content;

    @ApiModelProperty(value = "榜单发布单位, 从登录信息中获取")
    private String unitName;

    @ApiModelProperty(value = "视频URL地址")
    private String videoUrl;

    @ApiModelProperty(value = "状态：待揭榜、已签约、解决中、已解决 (从字典中获取)", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "状态：待揭榜、已签约、解决中、已解决 (从字典中获取)")
    private String statusName;

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    private Date createAt;

}
