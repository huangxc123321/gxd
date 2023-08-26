package com.gxa.jbgsw.business.controller;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.business.client.CompanyApi;
import com.gxa.jbgsw.business.entity.Company;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.ProvinceCityDistrictFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.CompanyService;
import com.gxa.jbgsw.business.service.HarvestService;
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
    ProvinceCityDistrictFeignApi provinceCityDistrictFeignApi;
    @Resource
    BillboardService billboardService;
    @Resource
    HarvestService harvestService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(CompanyDTO companyDTO) {
        Company company = mapperFacade.map(companyDTO, Company.class);
        company.setCreateAt(new Date());
        if(company.getProvinceId() != null && StrUtil.isBlank(company.getProvinceName())){
            ProvinceCityDistrictVO p = provinceCityDistrictFeignApi.getProvinceCityDistrictById(company.getProvinceId());
            if(p != null){
                company.setProvinceName(p.getProvinceName());
            }
        }
        if(company.getCityId() != null && StrUtil.isBlank(company.getCityName())){
            ProvinceCityDistrictVO c = provinceCityDistrictFeignApi.getProvinceCityDistrictById(company.getCityId());
            if(c != null){
                company.setCityName(c.getCityName());
            }
        }
        if(company.getAreaId() != null && StrUtil.isBlank(company.getAreaName())){
            ProvinceCityDistrictVO a = provinceCityDistrictFeignApi.getProvinceCityDistrictById(company.getAreaId());
            if(a != null){
                company.setAreaName(a.getAreaName());
            }
        }

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
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyService.getById(id);
        CompanyResponse companyResponse = mapperFacade.map(company, CompanyResponse.class);
        DictionaryDTO tradeType = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), companyResponse.getTradeType());
        if(tradeType != null){
            companyResponse.setTradeTypeName(tradeType.getDicValue());
        }
        DictionaryDTO t = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.enterprise_type.name(), String.valueOf(companyResponse.getType()));
        if(t != null){
            companyResponse.setTypeName(t.getDicValue());
        }

        return companyResponse;
    }

    @Override
    public PageResult<CompanyResponse> pageQuery(CompanyRequest request) {
        PageResult<CompanyResponse> pages = new PageResult<>();

        PageResult<Company> pageResult = companyService.pageQuery(request);
        List<Company> list = pageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<CompanyResponse> responses = mapperFacade.mapAsList(list, CompanyResponse.class);
            responses.forEach(s->{
                DictionaryDTO tradeType = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), s.getTradeType());
                if(tradeType != null){
                    s.setTradeTypeName(tradeType.getDicValue());
                }
                DictionaryDTO t = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.enterprise_type.name(), String.valueOf(s.getType()));
                if(t != null){
                    s.setTypeName(t.getDicValue());
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

    @Override
    public CompanyPCResponse getCompanyById4Pc(Long id) {
        Company company = companyService.getById(id);
        CompanyPCResponse companyPCResponse = mapperFacade.map(company, CompanyPCResponse.class);
        DictionaryDTO tradeType = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), companyPCResponse.getTradeType());
        if(tradeType != null){
            companyPCResponse.setTradeTypeName(tradeType.getDicValue());
        }
        DictionaryDTO t = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.enterprise_type.name(), String.valueOf(companyPCResponse.getType()));
        if(t != null){
            companyPCResponse.setTypeName(t.getDicValue());
        }

        // 发布榜单
        List<BillboardResponse> billboards = billboardService.getBillboardByUnitName(company.getName());
        companyPCResponse.setBillboards(billboards);

        // 发布成果
        List<HarvestResponse> harvests = harvestService.getHarvestByUnitName(company.getName());
        companyPCResponse.setHarvests(harvests);

        return companyPCResponse;
    }

    @Override
    public CompanyDTO getCompanyByUnitName(String unitName) {
        return companyService.getCompanyByName(unitName);
    }
}
