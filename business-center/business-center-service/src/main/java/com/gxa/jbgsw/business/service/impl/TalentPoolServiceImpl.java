package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.entity.Company;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.mapper.TalentPoolMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardTalentRelatedService;
import com.gxa.jbgsw.business.service.CompanyService;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    BillboardTalentRelatedService billboardTalentRelatedService;
    @Resource
    CompanyService companyService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        talentPoolMapper.deleteBatchIds(list);

        // 删除相关连的数据
        billboardTalentRelatedService.deleteByTalentId(list);
    }

    @Override
    public void add(TalentPoolPO talentPoolPO) {
        TalentPool talentPool = mapperFacade.map(talentPoolPO, TalentPool.class);
        talentPool.setId(null);
        talentPool.setCreateAt(new Date());

        StringBuffer sb = new StringBuffer();
        StringBuffer ts = new StringBuffer();
        // 技术领域
        if(talentPool.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(talentPool.getTechDomain());
            if(tfc != null){
                ts.append(tfc.getName());
                ts.append(CharUtil.COMMA);
            }
        }
        if(talentPool.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(talentPool.getTechDomain1());
            if(tfc1 != null){
                ts.append(tfc1.getName());
                ts.append(CharUtil.COMMA);
            }
        }
        if(talentPool.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(talentPool.getTechDomain2());
            if(tfc2 != null){
                ts.append(tfc2.getName());
                ts.append(CharUtil.COMMA);
            }
        }
        // 技术领域
        sb.append(ts.toString());

        if(talentPool.getProfessional() != null){
            DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(talentPool.getProfessional()));
            if(dictionaryDTO != null){
                sb.append(",").append(dictionaryDTO.getDicValue());
            }
        }
        sb.append(talentPool.getName()).append(",").append(talentPool.getHighestEdu()).append(",")
                .append(talentPool.getJob()).append(",").append(talentPool.getResearchDirection());
        talentPool.setQueryKeys(sb.toString());

        // this.save(talentPool);
        // 这个有返回ID
        talentPoolMapper.insert(talentPool);

        // 帅才匹配榜单
        String key = RedisKeys.TALENT_RELATED_RECOMMEND_TASK + talentPool.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(talentPool.getId()), 1, TimeUnit.MINUTES);
    }

    @Override
    public void updateTalentPool(TalentPoolUpdateDTO talentPoolPO) throws BizException {
        TalentPool talentPool = talentPoolMapper.selectById(talentPoolPO.getId());
        // talentPoolDTO 包含null全部拷贝
        BeanUtil.copyProperties(talentPoolPO, talentPool);

        talentPoolMapper.updateById(talentPool);

        // 帅才匹配榜单
        String key = RedisKeys.TALENT_RELATED_RECOMMEND_TASK + talentPool.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(talentPool.getId()), 1, TimeUnit.MINUTES);
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

    @Override
    public List<String> getUnits(String unitName) {
        LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(unitName)){
            lambdaQueryWrapper.like(TalentPool::getUnitName, unitName);
        }

        List<TalentPool> talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);
        if(talentPools != null && talentPools.size() >0){
            List<String> units = new ArrayList<>();
            talentPools.stream().forEach(s->{
                units.add(s.getUnitName());
            });
            // 去重返回
            return units.stream().distinct().collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<TalentPool> getRelatedTalentByCompanyId(Long id) {
        Company company = companyService.getById(id);
        if(company == null){
            return new ArrayList<>();
        }
        String tradeType = company.getTradeType();
        String tradeTypeName = null;
        if(StrUtil.isNotBlank(tradeType)){
            DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.trade_type.name(), tradeType);
            if(dic != null){
                tradeTypeName = dic.getDicValue();
            }
        }

        LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tradeTypeName)){
            lambdaQueryWrapper.likeLeft(TalentPool::getQueryKeys, tradeTypeName);
        }
        lambdaQueryWrapper.orderByDesc(TalentPool::getCreateAt);
        lambdaQueryWrapper.last(" LIMIT 1 ");
        List<TalentPool> talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);

        // 取最新一条
        if(talentPools == null || talentPools.size() == 0){
            LambdaQueryWrapper<TalentPool> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TalentPool::getCreateAt);
            wrapper.last(" LIMIT 1 ");
             talentPools = talentPoolMapper.selectList(wrapper);
        }

        return talentPools;
    }

}
