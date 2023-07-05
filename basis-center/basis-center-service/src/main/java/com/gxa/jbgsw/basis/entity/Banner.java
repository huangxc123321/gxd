package com.gxa.jbgsw.basis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_banner")
@ApiModel(value="Banner对象", description="广告位信息")
public class Banner implements Serializable {


    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "类型： 0 pc , 1 app")
    private Integer type;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer seqNo;

    @ApiModelProperty(value = "图片链接地址")
    private String links;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "生效时间")
    private Date effectAt;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

}
