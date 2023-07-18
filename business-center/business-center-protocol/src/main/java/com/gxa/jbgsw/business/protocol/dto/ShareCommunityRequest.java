package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShareCommunityRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "分享类型：0 文章  1 视频")
    private Integer type;

    @ApiModelProperty(value = "分享开始日期")
    private String startDate;

    @ApiModelProperty(value = "分享结束日期")
    private String endDate;

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过")
    private Integer status;


}
