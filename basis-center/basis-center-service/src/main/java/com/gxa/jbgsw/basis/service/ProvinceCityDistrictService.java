package com.gxa.jbgsw.basis.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.ProvinceCityDistrict;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryRequest;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryResponse;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 省市县数据表 服务类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
public interface ProvinceCityDistrictService extends IService<ProvinceCityDistrict> {

    PageResult<ProvinceCityDistrictQueryResponse> pageQuery(ProvinceCityDistrictQueryRequest request);

    List<ProvinceCityDistrictQueryResponse> pageSonQuery(String pid);

    ProvinceCityDistrictVO getProvinceCityDistrictById(Integer id);

    ProvinceCityDistrictVO getProvinceCityDistrictByName(String name);
}
