package com.gxa.jbgsw.basis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 省市县数据表
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_province_city_district")
@ApiModel(value="ProvinceCityDistrict对象", description="省市县数据表")
public class ProvinceCityDistrict implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "地区代码")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "当前地区的上一级地区代码")
    private Long pid;

    @ApiModelProperty(value = "地区名称")
    private String name;


}
