package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by mac on 2023/7/30.
 */
public class ShareCommuntiyRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "状态：  0 待审核  1 已审核  2 未通过" , hidden = true)
    private Integer status = 1;

}
