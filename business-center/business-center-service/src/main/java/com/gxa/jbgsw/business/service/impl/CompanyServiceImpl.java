package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Company;
import com.gxa.jbgsw.business.mapper.CompanyMapper;
import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyRequest;
import com.gxa.jbgsw.business.service.CompanyService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author huangxc
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Resource
    CompanyMapper companyMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        companyMapper.deleteBatchIds(list);
    }

    @Override
    public void updateCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.selectById(companyDTO.getId());
        BeanUtils.copyProperties(companyDTO, company, CopyPropertionIngoreNull.getNullPropertyNames(company));

        companyMapper.updateById(company);
    }

    @Override
    public PageResult<Company> pageQuery(CompanyRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<Company> companies = companyMapper.pageQuery(request);
        PageInfo<Company> pageInfo = new PageInfo<>(companies);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Company>>() {
        }.build(), new TypeBuilder<PageResult<Company>>() {}.build());
    }
}