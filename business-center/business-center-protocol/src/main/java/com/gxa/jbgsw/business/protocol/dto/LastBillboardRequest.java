package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class LastBillboardRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "0 降序 1 升序")
    private Integer status = 0;

    @ApiModelProperty(value = "0 降序 1 升序")
    private Integer amount  = 0;

    @ApiModelProperty(value = "0 降序 1 升序")
    private Integer createAt  = 0;

}
