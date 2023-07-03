package com.gxa.jbgsw.basis.protocol.dto;


import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;



@Data
public class DictionaryRequest  extends PageRequest implements Serializable {
    @ApiModelProperty(value = "字典类型ID")
    private Long typeId;
}

