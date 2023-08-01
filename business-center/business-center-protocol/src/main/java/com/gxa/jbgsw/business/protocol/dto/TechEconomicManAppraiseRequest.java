package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TechEconomicManAppraiseRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "技术经纪人")
    private Long userId;

    @ApiModelProperty(value = "是否有内容评价: 默认:false,  如果选择有：传 true")
    private boolean isHaveContent = false ;


}
