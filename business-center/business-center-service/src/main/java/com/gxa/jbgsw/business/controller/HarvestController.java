package com.gxa.jbgsw.business.controller;


import cn.hutool.core.util.CharUtil;
import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.client.HarvestApi;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.PatentService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "成果管理")
public class HarvestController implements HarvestApi {
    @Resource
    HarvestService harvestService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    PatentService patentService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public PageResult<HarvestResponse> pageQuery(HarvestRequest request) {
        PageResult<HarvestResponse> pages = new PageResult<>();

        PageResult<HarvestResponse> pageResult = harvestService.pageQuery(request);
        List<HarvestResponse> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            harvests.forEach(s->{
                // 技术领域
                if(s.getTechDomain() != null){
                    TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(s.getTechDomain());
                    if(tfc != null){
                        s.setTechDomainName(tfc.getName());
                    }
                }
                if(s.getTechDomain1() != null){
                    TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(s.getTechDomain1());
                    if(tfc1 != null){
                        s.setTechDomain1Name(tfc1.getName());
                    }
                }
                if(s.getTechDomain2() != null){
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(s.getTechDomain2());
                    if(tfc2 != null){
                        s.setTechDomain2Name(tfc2.getName());
                    }
                }
            });
            pages.setList(harvests);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public void add(HavestDTO havestDTO) {
        Harvest harvest = mapperFacade.map(havestDTO, Harvest.class);
        harvest.setCreateAt(new Date());

        // name + maturity_level + tech_domain
        // 组装keys
        StringBuffer sb = new StringBuffer();
        // 标题
        sb.append(havestDTO.getName());
        sb.append(CharUtil.COMMA);
        // 成熟度
        DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(havestDTO.getMaturityLevel()));
        if(dic != null){
            sb.append(dic.getDicValue());
        }
        sb.append(CharUtil.COMMA);
        // 技术领域
        if(havestDTO.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(havestDTO.getTechDomain());
            if(tfc != null){
                sb.append(tfc.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(havestDTO.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(havestDTO.getTechDomain1());
            if(tfc1 != null){
                sb.append(tfc1.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(havestDTO.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(havestDTO.getTechDomain2());
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
            }
        }

        harvest.setQueryKeys(sb.toString());
        harvestService.saveHarvest(harvest, havestDTO.getPatents());

        // 发布成果： 匹配榜单
        String key = RedisKeys.HARVEST_RELATED_RECOMMEND_TASK + harvest.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(harvest.getId()), 1, TimeUnit.MINUTES);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        harvestService.deleteBatchIds(ids);
    }

    @Override
    public void update(HavestDTO havestDTO) {
        Harvest harvest = harvestService.getById(havestDTO.getId());

        BeanUtils.copyProperties(havestDTO, harvest);

        // name + maturity_level + tech_domain
        // 组装keys
        StringBuffer sb = new StringBuffer();
        // 标题
        sb.append(harvest.getName());
        sb.append(CharUtil.COMMA);
        // 成熟度
        DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(harvest.getMaturityLevel()));
        if(dic != null){
            sb.append(dic.getDicValue());
        }
        sb.append(CharUtil.COMMA);
        // 技术领域
        if(harvest.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(harvest.getTechDomain());
            if(tfc != null){
                sb.append(tfc.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(harvest.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(harvest.getTechDomain1());
            if(tfc1 != null){
                sb.append(tfc1.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(havestDTO.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(havestDTO.getTechDomain2());
            if(tfc2 != null){
                sb.append(tfc2.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        harvest.setQueryKeys(sb.toString());

        harvestService.updateHarvest(harvest, havestDTO.getPatents());

    }

    @Override
    public List<HavestDTO> getHarvesByHolder(String holder) {
        List<Harvest> harvests = harvestService.getHarvesByHolder(holder);
        if(harvests == null){
            return new ArrayList<>();
        }

        return mapperFacade.mapAsList(harvests, HavestDTO.class);
    }

    @Override
    public HavestDetailInfo detail(Long id) {
        Harvest harvest = harvestService.getById(id);

        HavestDetailInfo havestDetailInfo = mapperFacade.map(harvest, HavestDetailInfo.class);
        // 技术领域
        StringBuffer sb = new StringBuffer();
        // 技术领域
        if(harvest.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(harvest.getTechDomain());
            if(tfc != null){
                sb.append(tfc.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(harvest.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(harvest.getTechDomain1());
            if(tfc1 != null){
                sb.append(tfc1.getName());
                sb.append(CharUtil.COMMA);
            }
        }
        if(harvest.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(harvest.getTechDomain2());
            if(tfc2 != null){
                sb.append(tfc2.getName());
            }
        }
        havestDetailInfo.setTechKeys(sb.toString().replace("所有分类,", ""));
        havestDetailInfo.setTechDomainName(sb.toString().replace("所有分类,", ""));

        // 成熟度
        DictionaryDTO dicMaturityLevel = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(harvest.getMaturityLevel()));
        if(dicMaturityLevel != null){
            havestDetailInfo.setMaturityLevelName(dicMaturityLevel.getDicValue());
        }

        return havestDetailInfo;
    }

    @Override
    public HavestPO getHavestById(Long id) {
        Harvest harvest = harvestService.getById(id);
        HavestPO havestPO = mapperFacade.map(harvest, HavestPO.class);

        // 技术领域
        if(havestPO.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain());
            if(tfc != null){
                havestPO.setTechDomainName(tfc.getName());
            }
        }
        if(havestPO.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain1());
            if(tfc1 != null){
                havestPO.setTechDomain1Name(tfc1.getName());
            }
        }
        if(havestPO.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain2());
            if(tfc2 != null){
                havestPO.setTechDomain2Name(tfc2.getName());
            }
        }

        if(harvest.getIsPatent() != null && harvest.getIsPatent().equals(Integer.valueOf(1))){
            List<PatentDTO> patents = patentService.getPatentByPid(id);
            havestPO.setPatents(patents);
        }
        return havestPO;
    }

    @Override
    public List<RecommendHavestResponse> getRecommendHavest() {
        return harvestService.getRecommendHavest();
    }

    @Override
    public PageResult<HarvestResponse> pageMyHarvestQuery(HarvestRequest request) {
        PageResult<HarvestResponse> pages = new PageResult<>();

        PageResult<Harvest> pageResult = harvestService.pageMyHarvestQuery(request);
        List<Harvest> harvests = pageResult.getList();
        if(CollectionUtils.isNotEmpty(harvests)){
            List<HarvestResponse> responses = mapperFacade.mapAsList(harvests, HarvestResponse.class);
            responses.forEach(s->{

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
    public List<String> getContacts(String contacts) {
        return harvestService.getContacts(contacts);
    }

    @Override
    public List<String> getHoloder(String holder) {
        return harvestService.getHoloder(holder);
    }

}

