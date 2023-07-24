package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.CompanyApi;
import com.gxa.jbgsw.business.entity.Company;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyRequest;
import com.gxa.jbgsw.business.protocol.dto.CompanyResponse;
import com.gxa.jbgsw.business.protocol.dto.HarvestResponse;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.CompanyService;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "企业管理")
@RestController
@Slf4j
public class CompanyController implements CompanyApi {
    @Resource
    CompanyService companyService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(CompanyDTO companyDTO) {
        Company company = mapperFacade.map(companyDTO, Company.class);
        company.setCreateAt(new Date());

        companyService.save(company);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        companyService.deleteBatchIds(ids);
    }

    @Override
    public void update(CompanyDTO companyDTO) {
        companyService.updateCompany(companyDTO);
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyService.getById(id);
        CompanyDTO companyDTO = mapperFacade.map(company, CompanyDTO.class);
        return companyDTO;
    }

    @Override
    public PageResult<CompanyResponse> pageQuery(CompanyRequest request) {
        PageResult<CompanyResponse> pages = new PageResult<>();

        PageResult<Company> pageResult = companyService.pageQuery(request);
        List<Company> list = pageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<CompanyResponse> responses = mapperFacade.mapAsList(list, CompanyResponse.class);
            responses.forEach(s->{
                // TODO: 2023/7/4 0004 暂时不转换
                DictionaryDTO tradeType = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), s.getTradeType());
                if(tradeType != null){
                    s.setTradeTypeName(tradeType.getDicValue());
                }

            });
            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }
        return pages;
    }
}
