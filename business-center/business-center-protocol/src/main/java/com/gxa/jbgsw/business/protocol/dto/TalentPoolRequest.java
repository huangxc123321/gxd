package com.gxa.jbgsw.business.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangxc
 */
@Data
public class TalentPoolRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索字段")
    private String searchFiled;

    @ApiModelProperty(value = "省份地区地址： 省、市、区一级别的地区ID")
    private String addrId;

    @ApiModelProperty(value = "技术领域(第一级)")
    private Long techDomain1;

    @ApiModelProperty(value = "技术领域(第二级)：字典中获取")
    private Long techDomain2;

    @ApiModelProperty(value = "技术领域显示名称(第三级，也就是最后)")
    private Long techDomain;

    @ApiModelProperty(value = "学历：字典获取(highest_edu)")
    private String highestEdu;

    @ApiModelProperty(value = "性别： 0 男 1女")
    private Integer sex;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "状态：0 待审核, 1 已审核, 2未通过")
    private Integer status;

}
