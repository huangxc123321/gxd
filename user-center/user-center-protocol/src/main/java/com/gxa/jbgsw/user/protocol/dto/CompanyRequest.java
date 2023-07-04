package com.gxa.jbgsw.user.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author huangxc
 */
@Data
public class CompanyRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "企业类型名称：字典中获取")
    private Integer type;

    @ApiModelProperty(value = "所属行业：字典中获取")
    private String tradeType;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

}
