package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.Company;
import com.gxa.jbgsw.business.protocol.dto.BillboardIndexDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyRequest;
import com.gxa.jbgsw.business.protocol.dto.CompanyResponse;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;


public interface CompanyService extends IService<Company> {

    void deleteBatchIds(Long[] ids);

    void updateCompany(CompanyDTO companyDTO);

    PageResult<Company> pageQuery(CompanyRequest request);

    CompanyDTO getCompanyByName(String unitName);

    void updateUnitName(String oldUnitName, String unitName);
}
