package com.gxa.jbgsw.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @Classname PageRequest
 * @Description TODO
 */
@Data
@ApiModel
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1316423930398233099L;
    @ApiModelProperty(notes = "页数（默认1）")
    @JsonProperty(value = "pageNum")
    private Integer pageNum = 1;
    @ApiModelProperty(notes = "纪录数（默认15）")
    @JsonProperty(value = "pageSize")
    private Integer pageSize = 15;
}
