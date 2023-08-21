package com.gxa.jbgsw.business.controller;


import cn.hutool.core.util.CharUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.client.TalentPoolApi;
import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolAuditingDTO;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolRequest;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolResponse;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolDetailInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "帅才库管理")
public class TalentPoolController implements TalentPoolApi {
    @Resource
    TalentPoolService talentPoolService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        talentPoolService.deleteBatchIds(ids);
    }

    @Override
    public PageResult<TalentPoolResponse> pageQuery(TalentPoolRequest request) {
        return talentPoolService.pageQuery(request);
    }

    @Override
    public void add(TalentPoolDTO talentPoolDTO) {
        talentPoolService.add(talentPoolDTO);
    }

    @Override
    public void update(TalentPoolDTO talentPoolDTO) {
        talentPoolService.updateTalentPool(talentPoolDTO);
    }

    @Override
    public List<TalentPoolDTO> getTalentPoolByTech(String key) {
        List<TalentPool> talentPools = talentPoolService.getTalentPoolByTech(key);

        if(CollectionUtils.isNotEmpty(talentPools)){
            return mapperFacade.mapAsList(talentPools, TalentPoolDTO.class);
        }
        return new ArrayList<>();
    }


    @Override
    public TalentPoolDetailInfo getTalentPoolDetailInfo(Long id) {
        TalentPool talentPool = talentPoolService.getById(id);

        TalentPoolDetailInfo talentPoolDetailInfo = mapperFacade.map(talentPool, TalentPoolDetailInfo.class);
        // 职称
        if(talentPoolDetailInfo != null && talentPoolDetailInfo.getProfessional() != null){
            DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(talentPoolDetailInfo.getProfessional()));
            if(dicProfessional != null){
                talentPoolDetailInfo.setProfessionalName(dicProfessional.getDicValue());
            }
        }
        // 技术领域
        StringBuffer sb = new StringBuffer();
        TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(talentPool.getTechDomain()));
        if(tfc1 != null){
            sb.append(tfc1.getName());
            sb.append(CharUtil.COMMA);
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
                TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                if(tfc3 != null){
                    sb.append(tfc3.getName());
                }
            }
        }
        talentPoolDetailInfo.setTechDomainName(sb.toString().replace("所有分类", ""));


        return talentPoolDetailInfo;
    }


    @Override
    public TalentPoolDTO getTalentPoolById(Long id) {
        TalentPool talentPool = talentPoolService.getById(id);

        TalentPoolDTO talentPoolDTO = mapperFacade.map(talentPool, TalentPoolDTO.class);
        // 职称
        if(talentPoolDTO != null && talentPoolDTO.getProfessional() != null){
            DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(talentPoolDTO.getProfessional()));
            if(dicProfessional != null){
                talentPoolDTO.setProfessionalName(dicProfessional.getDicValue());
            }
        }
        // 技术领域
        StringBuffer sb = new StringBuffer();
        TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(talentPool.getTechDomain()));
        if(tfc1 != null){
            sb.append(tfc1.getName());
            sb.append(CharUtil.COMMA);
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
                TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                if(tfc3 != null){
                    sb.append(tfc3.getName());
                }
            }
        }
        talentPoolDTO.setTechDomainName(sb.toString().replace("所有分类,", ""));


        return talentPoolDTO;
    }

    @Override
    public void updateStatus(TalentPoolAuditingDTO talentPoolAuditingDTO) {
        TalentPool talentPool = talentPoolService.getById(talentPoolAuditingDTO.getId());
        if(talentPool != null){
            talentPool.setStatus(talentPoolAuditingDTO.getStatus());
            talentPool.setAuditDate(talentPoolAuditingDTO.getAuditDate());
            talentPool.setAuditReason(talentPoolAuditingDTO.getAuditReason());
            talentPool.setAuditUserId(talentPoolAuditingDTO.getAuditUserId());
            talentPool.setAuditUserName(talentPoolAuditingDTO.getAuditUserName());

            talentPoolService.updateById(talentPool);
        }
    }

    @Override
    public List<String> getUnits(String unitName) {
        return talentPoolService.getUnits(unitName);
    }

}

