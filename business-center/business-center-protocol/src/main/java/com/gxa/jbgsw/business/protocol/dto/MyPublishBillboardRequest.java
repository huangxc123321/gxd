package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyPublishBillboardRequest extends PageRequest implements Serializable {
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "榜单类型： 0 政府榜 1 企业榜")
    private Integer type;


    @ApiModelProperty(value = "审核状态： 0 待审核  1 审核通过  2 审核不通过")
    private Integer auditStatus = 0;

}
