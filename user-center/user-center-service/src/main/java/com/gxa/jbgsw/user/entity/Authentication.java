package com.gxa.jbgsw.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 认证
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_authentication")
@ApiModel(value="Authentication对象", description="认证")
public class Authentication implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "认证类型：0：个人认证 1：企业认证 2：机构认证")
    private Integer type;

    @ApiModelProperty(value = "证件类型： 0 身份证 1 驾驶证")
    @TableId(value = "credentials_type", type = IdType.ID_WORKER_STR)
    private Integer credentialsType;

    @ApiModelProperty(value = "认证名称")
    private String name;

    @ApiModelProperty(value = "认证状态： 0 待审核  1 已通过 2 未通过")
    private Integer status;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "证件图片前面照片")
    private String imagesF;

    @ApiModelProperty(value = "证件图片后面照片")
    private String imagesB;

    @ApiModelProperty(value = "人证合一图片")
    private String imagesH;

    @ApiModelProperty(value = " 认证时间")
    private Date createDate;


}
