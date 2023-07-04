package com.gxa.jbgsw.user.mapper;

import com.gxa.jbgsw.user.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.user.protocol.dto.CompanyRequest;

import java.util.List;

/**
 * <p>
 * 企业信息 Mapper 接口
 * </p>
 *
 * @author huangxc
 * @since 2023-06-25
 */
public interface CompanyMapper extends BaseMapper<Company> {

    List<Company> pageQuery(CompanyRequest request);
}
