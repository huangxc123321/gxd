package com.gxa.jbgsw.basis.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangxc
 */
@Data
public class BannerRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "状态：0：已生效 1：待失效 2：已失传")
    private Integer status;

}
