package com.gxa.jbgsw.user.protocol.dto;

import com.gxa.jbgsw.common.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Mr. huang
 * @Date 2023/6/25 0025 17:14
 * @Version 2.0
 */
@Data
public class UserRequest extends PageRequest implements Serializable {
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "创建开始时间")
    private Date startDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date endDate;

    @ApiModelProperty(value = "认证状态： 0 待审核  1 已通过 2 未通过")
    private Integer status;

    @ApiModelProperty(value = "使用状态： 0 已使用  1 停用")
    private Integer useStauts;

}
