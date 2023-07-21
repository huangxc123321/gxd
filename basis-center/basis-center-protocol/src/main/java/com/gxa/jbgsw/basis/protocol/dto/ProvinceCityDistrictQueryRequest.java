package com.gxa.jbgsw.basis.protocol.dto;


import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ApiModel
@ToString
public class ProvinceCityDistrictQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "父ID")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

}
