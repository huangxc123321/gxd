package com.gxa.jbgsw.user.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ApiModel
@ToString
public class RoleRequest extends PageRequest implements Serializable {

    private String name;

}
