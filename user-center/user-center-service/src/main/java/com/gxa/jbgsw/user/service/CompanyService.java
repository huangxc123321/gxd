package com.gxa.jbgsw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.entity.Company;
import com.gxa.jbgsw.user.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.user.protocol.dto.CompanyRequest;


public interface CompanyService  extends IService<Company> {

    void deleteBatchIds(Long[] ids);

    void updateCompany(CompanyDTO companyDTO);

    PageResult<Company> pageQuery(CompanyRequest request);
}
