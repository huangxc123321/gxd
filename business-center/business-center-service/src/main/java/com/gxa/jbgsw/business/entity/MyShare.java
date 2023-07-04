package com.gxa.jbgsw.business.entity;

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
@TableName("t_my_share")
@ApiModel(value="MyShare对象", description="我的分享")
public class MyShare implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "分享类型： 0政府榜 1企业榜 2成果 3政策 4帅才")
    private Integer shareType;

    @ApiModelProperty(value = "分享关联的ID")
    private Long pid;

    @ApiModelProperty(value = "分享时间")
    private Date createAt;


}
