package com.gxa.jbgsw.basis.protocol.dto;


import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class DictionaryTypePageRequest extends PageRequest implements Serializable {
    private String code;
}
