package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class DictionaryTypeRequest implements Serializable {
    private String code;
}
