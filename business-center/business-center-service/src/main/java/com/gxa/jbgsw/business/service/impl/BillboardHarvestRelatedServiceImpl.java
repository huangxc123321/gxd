package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardHarvestRelatedMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.common.utils.ComputeSimilarityRatio;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.common.utils.StrCommon;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 榜单与成果的匹配关联表 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
@Service
public class BillboardHarvestRelatedServiceImpl extends ServiceImpl<BillboardHarvestRelatedMapper, BillboardHarvestRelated> implements BillboardHarvestRelatedService {
    @Resource
    HarvestService harvestService;
    @Resource
    BillboardService billboardService;
    @Resource
    BillboardHarvestRelatedMapper billboardHarvestRelatedMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void addHarvestRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // 获取一百条
        LambdaQueryWrapper<Harvest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Harvest::getCreateAt);
        lambdaQueryWrapper.last("LIMIT 100");
        List<Harvest> harvests = harvestService.list(lambdaQueryWrapper);

        if(harvests != null && harvests.size()>0){
            List<BillboardHarvestRelated> relateds = new ArrayList<>();
            List<BillboardHarvestRelated> finalRelateds = relateds;
            for(int i=0; i<harvests.size(); i++){
                int sorce = 0;
                Harvest s = harvests.get(i);
                String title = billboard.getTitle();
                // 匹配标题 === 成果名称
                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, s.getName());
                sameWords = StrCommon.getResult(sameWords);

                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配3个字以下 0 分，匹配3个字给1分， 匹配4个字以上给2分
                    if(sameWords.length()>=4 && sameWords.length() <5){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=5){
                        sorce = sorce +2;
                    }
                }

                // 技术关键词 === 技术领域
                String techKeys = billboard.getTechKeys();
                techKeys = StrCommon.clear(techKeys);

                StringBuffer sb = new StringBuffer();

                TechnicalFieldClassifyDTO tfc1 = null;
                if(s.getTechDomain1() != null){
                    try{
                        tfc1 = getOneById(s.getTechDomain1());
                        if(tfc1 != null){
                            sb.append(tfc1.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain1() error");
                    }
                }

                TechnicalFieldClassifyDTO tfc2 = null;
                if(s.getTechDomain2() != null){
                    try{
                        tfc2 = getByPid(tfc1, s.getTechDomain2());
                        if(tfc2 != null){
                            sb.append(tfc2.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain2() error");
                    }
                }
                if(s.getTechDomain() != null){
                    try{
                        TechnicalFieldClassifyDTO tfc = getByPid(tfc2, s.getTechDomain());
                        if(tfc != null){
                            sb.append(tfc.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain() error");
                    }
                }

                String resultFiled = sb.toString() +  s.getAppyDomain();
                resultFiled = StrCommon.clear(resultFiled);

                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, sb.toString());
                techWords = StrCommon.getResult(techWords);
                if(StrUtil.isNotBlank(techWords)){
                    // double num = ComputeSimilarityRatio.SimilarDegree(techKeys,s.getQueryKeys());
                    if(techWords.length()>=6){
                        sorce = sorce +5;
                    }else if(techWords.length()>=5){
                        sorce = sorce +4;
                    }else if(techWords.length()>=4){
                        sorce = sorce +3;
                    }else if(techWords.length()>=3){
                        sorce = sorce +2;
                    }
                }

                // 行业 === 应用领域(appyDomain)
                Integer categories = billboard.getCategories();
                // 行业名称
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                    String techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, resultFiled);
                    techSameWorkds = StrCommon.getResult(techSameWorkds);

                    if(StrUtil.isNotBlank(techSameWorkds)){
                        // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                        if(techSameWorkds.length()>=4 && techSameWorkds.length() <5){
                            sorce = sorce +1;
                        }else if(techSameWorkds.length()>=5){
                            sorce = sorce +2;
                        }
                    }
                }

                // 榜单发布地区
                boolean isAddress = false;
                String address = billboard.getProvinceName()+billboard.getCityName()+billboard.getAreaName();
                // 成果的单位
                String unitName = s.getUnitName();
                // 匹配地区
                String addressWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(address, unitName);
                addressWorkds = StrCommon.getAddress(addressWorkds);

                if(StrUtil.isNotBlank(addressWorkds)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分
                    if(addressWorkds.length()>=4){
                        sorce = sorce +1;
                        isAddress = true;
                    }
                }


                BillboardHarvestRelated related = new BillboardHarvestRelated();
                related.setBillboardId(billboardId);
                related.setSStar(Double.valueOf(sorce>5?5:sorce));
                related.setHarvestId(s.getId());
                related.setCreateAt(new Date());

                if(related.getSStar() >2 ){
                    // 把只是匹配地区的排除，因为有些只是地区匹配，行业风牛马不相及
                    if(isAddress && related.getSStar() != 1){
                        relateds.add(related);
                    }else if(related.getSStar() >2){
                        relateds.add(related);
                    }
                }
            }

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardHarvestRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            if(CollectionUtils.isNotEmpty(relateds)){
                this.saveBatch(relateds);
            }
        }


    }

    @Override
    public List<BillboardHarvestRelatedResponse> getHarvestRecommend(Long billboardId) {
        return billboardHarvestRelatedMapper.getHarvestRecommend(billboardId);
    }

    @Override
    public List<HavestCollaborateDTO> getHarvestRecommendByHarvestId(Long harvestId) {
        return billboardHarvestRelatedMapper.getHarvestRecommendByHarvestId(harvestId);
    }

    @Override
    public List<BillboardResponse> getHarvestByHarvestId(Long harvestId) {
        return billboardHarvestRelatedMapper.getHarvestByHarvestId(harvestId);
    }

    @Override
    public List<BillboardHarvestRelatedResponse> getBillboardstByHarvestId(Long harvestId) {
        return billboardHarvestRelatedMapper.getBillboardstByHarvestId(harvestId);
    }

    @Override
    public List<RelateHavestDTO> getRelatedHavestByBillboardId(Long id) {
        List<RelateHavestDTO> relateHavests = billboardHarvestRelatedMapper.getRelatedHavestByBillboardId(id);
        if(CollectionUtils.isEmpty(relateHavests)){
            // 如果没有数据，就默认的取
            Billboard billboard = billboardService.getById(id);
            String[] keys = billboard.getTechKeys().split(",");
            List<Harvest>  harvests = harvestService.getHarvesByTechDomain(keys[0]);
            if(CollectionUtils.isNotEmpty(harvests)){
                if(harvests.size()>=3){
                    harvests = harvests.subList(0,2);
                    List<RelateHavestDTO> havestDTOList = mapperFacade.mapAsList(harvests, RelateHavestDTO.class);
                    return havestDTOList;
                }
            }
        }

        return relateHavests;
    }

    @Override
    public void deleteByHarvestId(List<Long> list) {
        LambdaQueryWrapper<BillboardHarvestRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardHarvestRelated::getHarvestId, list);

        billboardHarvestRelatedMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void addBillboardRelated(Long harvestId) {
        Harvest harvest = harvestService.getById(harvestId);

        LambdaQueryWrapper<BillboardHarvestRelated> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BillboardHarvestRelated::getHarvestId, harvestId);
        queryWrapper.eq(BillboardHarvestRelated::getStatus, 0);
        billboardHarvestRelatedMapper.delete(queryWrapper);

        // 获取一百条
        LambdaQueryWrapper<Billboard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode())
                          .eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode())
                          .orderByDesc(Billboard::getCreateAt)
                          .last("LIMIT 100");

        List<Billboard> billboards = billboardService.list(lambdaQueryWrapper);
        if(billboards != null && billboards.size()>0){
            List<BillboardHarvestRelated> relateds = new ArrayList<>();
            List<BillboardHarvestRelated> finalRelateds = relateds;

            for(int i=0; i<billboards.size(); i++){
                int sorce = 0;
                Billboard s = billboards.get(i);
                // 成果名称 === 榜单标题
                String name = harvest.getName();
                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(s.getTitle(), name);
                sameWords = StrCommon.getResult(sameWords);

                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配3个字以下 0 分，匹配3个字给1分， 匹配4个字以上给2分
                    if(sameWords.length()>=4 && sameWords.length() <5){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=5){
                        sorce = sorce +2;
                    }
                }

                // 榜单技术关键词 === 成果技术领域
                String techKeys = s.getTechKeys();
                techKeys = StrCommon.clear(techKeys);

                StringBuffer sb = new StringBuffer();
                TechnicalFieldClassifyDTO tfc1 = null;
                if(harvest.getTechDomain1() != null){
                    try{
                        tfc1 = getOneById(harvest.getTechDomain1());
                        if(tfc1 != null){
                            sb.append(tfc1.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain1() error");
                    }
                }

                TechnicalFieldClassifyDTO tfc2 = null;
                if(harvest.getTechDomain2() != null){
                    try{
                        tfc2 = getByPid(tfc1, harvest.getTechDomain2());
                        if(tfc2 != null){
                            sb.append(tfc2.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain2() error");
                    }
                }
                if(harvest.getTechDomain() != null){
                    try{
                        TechnicalFieldClassifyDTO tfc = getByPid(tfc2, harvest.getTechDomain());
                        if(tfc != null){
                            sb.append(tfc.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain() error");
                    }
                }

                String resultFiled = StrCommon.clear(harvest.getAppyDomain() + sb.toString());
                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(resultFiled, techKeys);
                techWords = StrCommon.getResult(techWords);
                if(StrUtil.isNotBlank(techWords)){
                    // double num = ComputeSimilarityRatio.SimilarDegree(techKeys,harvest.getQueryKeys());
                    // 如果匹配1个字以下 0 分，匹配1个字给1分， 匹配2个字以上给2分
                    if(techWords.length()>=6){
                        sorce = sorce +5;
                    }else if(techWords.length()>=5){
                        sorce = sorce +4;
                    }else if(techWords.length()>=4){
                        sorce = sorce +3;
                    }else if(techWords.length()>=3){
                        sorce = sorce +2;
                    }
                }

                // 行业 === 应用领域
                Integer categories = s.getCategories();
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                    String techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(resultFiled, categoriesName);
                    techSameWorkds = StrCommon.getResult(techSameWorkds);

                    if(StrUtil.isNotBlank(techSameWorkds)){
                        // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                        if(techSameWorkds.length()>=4 && techSameWorkds.length() <5){
                            sorce = sorce +1;
                        }else if(techSameWorkds.length()>=5){
                            sorce = sorce +2;
                        }
                    }
                }

                // 榜单发布地区
                boolean isAddress = false;
                String billboardAddress = s.getProvinceName()+s.getCityName()+s.getAreaName();
                String address = harvest.getUnitName();
                // 匹配地区
                String addressWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(billboardAddress,address);
                addressWorkds = StrCommon.getAddress(addressWorkds);

                if(StrUtil.isNotBlank(addressWorkds)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分
                    if(addressWorkds.length()>=4){
                        sorce = sorce +1;
                        isAddress = true;
                    }
                }

                BillboardHarvestRelated related = new BillboardHarvestRelated();
                related.setBillboardId(s.getId());
                related.setSStar(Double.valueOf(sorce>5?5:sorce));
                related.setHarvestId(harvest.getId());
                related.setCreateAt(new Date());

                if(related.getSStar() >2 ){
                    // 把只是匹配地区的排除，因为有些只是地区匹配，行业风牛马不相及
                    if(isAddress && related.getSStar() != 1){
                        relateds.add(related);
                    }else if(related.getSStar() >2){
                        relateds.add(related);
                    }
                }
            }


            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardHarvestRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            if(CollectionUtils.isNotEmpty(relateds)){
                this.saveBatch(relateds);
            }
        }

    }

    @Override
    public void deleteByBillboardId(List<Long> list) {
        LambdaQueryWrapper<BillboardHarvestRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardHarvestRelated::getBillboardId, list);

        billboardHarvestRelatedMapper.delete(lambdaQueryWrapper);
    }

    public DictionaryDTO getByCache(String typeCode, String code) {
        DictionaryDTO dictionary = new DictionaryDTO();

        String keys = RedisKeys.DICTIONARY_TYPE_VALUE + typeCode;
        String json = stringRedisTemplate.opsForValue().get(keys);
        JSONArray array = JSONArray.parseArray(json);
        for(int i=0; i<array.size(); i++){
            JSONObject jsonObject = (JSONObject)array.get(i);
            DictionaryResponse response = JSONObject.toJavaObject(jsonObject, DictionaryResponse.class);
            if(response.getDicCode().equals(String.valueOf(code))){
                dictionary.setDicCode(response.getDicCode());
                dictionary.setDicValue(response.getDicValue());
                dictionary.setId(response.getId());
                dictionary.setTypeId(Long.valueOf(response.getTypeId()));

                break;
            }
        }

        return dictionary;
    }

    public TechnicalFieldClassifyDTO getOneById(Long id){
        String json = stringRedisTemplate.opsForValue().get(RedisKeys.TECH_DOMAIN_VALUE);
        List<TechnicalFieldClassifyDTO> responress = JSONArray.parseArray(json, TechnicalFieldClassifyDTO.class);
        List<TechnicalFieldClassifyDTO> one = responress.get(0).getChildren();

        for(int i=0; i<one.size(); i++){
            TechnicalFieldClassifyDTO dto = one.get(i);
            if(dto.getId().equals(id)){
                return dto;
            }
        }

        return null;
    }

    public TechnicalFieldClassifyDTO getByPid(TechnicalFieldClassifyDTO tfc, Long id){

        List<TechnicalFieldClassifyDTO> v = tfc.getChildren();

        for(int i=0; i<v.size(); i++){
            TechnicalFieldClassifyDTO dto = v.get(i);
            if(dto.getId().equals(id)){
                return dto;
            }
        }

        return null;
    }

}
