package com.gxa.jbgsw.basis.protocol.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WebsiteBottomDTO implements Serializable {

    @ApiModelProperty(value = "主键: 新增时不填写为null, 更新的时候需要用填写ID，不能为空")
    private Long id;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "app二维码")
    private String appQrCode;

    @ApiModelProperty(value = "微信二维码")
    private String wxQrCode;

    @ApiModelProperty(value = "备案信息")
    private String recordInfo;

    @ApiModelProperty(value = "links，友情链接，")
    private List<FriendlyLinkDTO> friendlyLinks;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createAt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateAt;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updateBy;

}
