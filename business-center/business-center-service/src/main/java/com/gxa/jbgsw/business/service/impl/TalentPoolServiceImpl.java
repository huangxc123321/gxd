package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.mapper.TalentPoolMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.BindException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 帅才库 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class TalentPoolServiceImpl extends ServiceImpl<TalentPoolMapper, TalentPool> implements TalentPoolService {
    @Resource
    TalentPoolMapper talentPoolMapper;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        talentPoolMapper.deleteBatchIds(list);
    }

    @Override
    public void add(TalentPoolDTO talentPoolDTO) {
        TalentPool talentPool = mapperFacade.map(talentPoolDTO, TalentPool.class);
        talentPool.setId(null);
        talentPool.setCreateAt(new Date());

        StringBuffer sb = new StringBuffer();
        StringBuffer ts = new StringBuffer();
        TechnicalFieldClassifyDTO t = technicalFieldClassifyFeignApi.getById(talentPoolDTO.getTechDomain());
        ts.append(t.getName());
        if(t != null && !t.getPid().equals(-1)){
            TechnicalFieldClassifyDTO t1 = technicalFieldClassifyFeignApi.getById(t.getPid());
            ts.append(",").append(t1.getName());
            if(t1 != null && !t1.getPid().equals(-1)){
                TechnicalFieldClassifyDTO t2 = technicalFieldClassifyFeignApi.getById(t1.getPid());
                ts.append(",").append(t2.getName());

                if(t2!=null && !t2.getPid().equals(-1)){
                    TechnicalFieldClassifyDTO t3 = technicalFieldClassifyFeignApi.getById(t2.getPid());
                    ts.append(",").append(t3.getName());
                }
            }
        }
        // 技术领域
        sb.append(ts.toString());

        sb.append(talentPoolDTO.getName()).append(",").append(talentPoolDTO.getHighestEdu()).append(",")
                .append(talentPoolDTO.getJob()).append(",").append(talentPoolDTO.getProfessionalName())
                .append(",").append(talentPoolDTO.getResearchDirection());
        talentPool.setQueryKeys(sb.toString());

        this.save(talentPool);
    }

    @Override
    public void updateTalentPool(TalentPoolDTO talentPoolDTO) throws BizException {
        TalentPool talentPool = talentPoolMapper.selectById(talentPoolDTO.getId());
        // talentPoolDTO有null就不需要替换talentPool
        BeanUtils.copyProperties(talentPoolDTO, talentPool, CopyPropertionIngoreNull.getNullPropertyNames(talentPool));

        talentPoolMapper.updateById(talentPool);
    }

    @Override
    public List<TalentPool> getTalentPoolByTech(String key) {
        LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(TalentPool::getTechDomain, key)
                .orderByDesc(TalentPool::getAuditDate);

        List<TalentPool> talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);

        return talentPools;
    }

    @Override
    public PageResult<SearchTalentsResponse> queryTalents(SearchTalentsRequest searchTalentsRequest) {
        PageHelper.startPage(searchTalentsRequest.getPageNum(), searchTalentsRequest.getPageSize());

        List<SearchTalentsResponse> talents = talentPoolMapper.queryTalents(searchTalentsRequest);
        if(CollectionUtils.isNotEmpty(talents)){
            PageInfo<SearchTalentsResponse> pageInfo = new PageInfo<>(talents);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<SearchTalentsResponse>>() {
            }.build(), new TypeBuilder<PageResult<SearchTalentsResponse>>() {}.build());
        }

        return new PageResult<SearchTalentsResponse>();
    }

    @Override
    public PageResult<TalentPoolResponse> pageQuery(TalentPoolRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<TalentPoolResponse> talents = talentPoolMapper.pageQuery(request);
        if(CollectionUtils.isNotEmpty(talents)){
            PageInfo<TalentPoolResponse> pageInfo = new PageInfo<>(talents);

            return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<TalentPoolResponse>>() {
            }.build(), new TypeBuilder<PageResult<TalentPoolResponse>>() {}.build());
        }

        return new PageResult<TalentPoolResponse>();
    }

}
