package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.mapper.BillboardTalentRelatedMapper;
import com.gxa.jbgsw.business.mapper.TalentPoolMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.BillboardTalentRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.TalentPoolService;
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
 * 榜单与帅才的匹配关联表 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
@Service
public class BillboardTalentRelatedServiceImpl extends ServiceImpl<BillboardTalentRelatedMapper, BillboardTalentRelated> implements BillboardTalentRelatedService {
    @Resource
    TalentPoolService talentPoolService;
    @Resource
    BillboardService billboardService;
    @Resource
    BillboardTalentRelatedMapper billboardTalentRelatedMapper;
    @Resource
    TalentPoolMapper talentPoolMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void addTalentRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // 获取一百条
        LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 条件：已审核
        lambdaQueryWrapper.eq(TalentPool::getStatus, 1);
        lambdaQueryWrapper.last("LIMIT 100");
        List<TalentPool> talentPools = talentPoolService.list(lambdaQueryWrapper);

        if(talentPools != null && talentPools.size()>0){
            // 分数
            List<BillboardTalentRelated> relateds = new ArrayList<>();
            List<BillboardTalentRelated> finalRelateds = relateds;

            for(int i=0; i<talentPools.size(); i++){
                int sorce = 0;
                String title = billboard.getTitle();
                TalentPool s = talentPools.get(i);

                // 榜单名称 ===== 研究方向
                String researchDirection = s.getResearchDirection();
                researchDirection = StrCommon.clear(researchDirection);

                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, researchDirection);
                sameWords = StrCommon.getResult(sameWords);

                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配3个字以下 0 分，匹配3个字给1分， 匹配4个字以上给2分
                    if(sameWords.length()>=6 && sameWords.length() <7){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=8){
                        sorce = sorce +2;
                    }
                }


                // 技术关键词 === 研究成果
                String techKeys = billboard.getTechKeys();
                techKeys = StrCommon.clear(techKeys);

                String resultFiled = s.getResearchDirection() + s.getRemark();
                resultFiled = StrCommon.clear(resultFiled);

                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, resultFiled);
                techWords = StrCommon.getResult(techWords);

                if(StrUtil.isNotBlank(techWords)){
                    //double num = ComputeSimilarityRatio.SimilarDegree(techKeys, s.getResearchDirection());
                    // 如果匹配1个字以下 0 分，匹配1个字给1分， 匹配2个字以上给2分
                    if(techWords.length()>=6){
                        sorce = sorce +5;
                    }else if(techWords.length()>=5){
                        sorce = sorce +4;
                    }else if(techWords.length()>=4){
                        sorce = sorce +2;
                    }


                // 行业  === 个人简介
                Integer categories = billboard.getCategories();
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                   // String remark =  s.getRemark();
                    String techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, resultFiled);
                    techSameWorkds = StrCommon.getResult(techSameWorkds);
                    if(StrUtil.isNotBlank(techSameWorkds)){
                        // 如果匹配2个字以上2分，匹配2个字给1分，其它 0分
                        if(techSameWorkds.length()>=5){
                            sorce = sorce +2;
                        }else if(techSameWorkds.length() ==4){
                            sorce = sorce +1;
                        }
                    }

                }


                /**
                 *  地区 === 地区
                 */
                // 榜单发布地区
                boolean isAddress = false;
                String billboradAddress = billboard.getProvinceName()+billboard.getCityName()+billboard.getAreaName();
                // 帅才地区
                String address = s.getProvinceName()+s.getCityName()+s.getAreaName();
                // 匹配地区
                String addressWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(billboradAddress, address);
                addressWorkds = StrCommon.getAddress(addressWorkds);

                if(StrUtil.isNotBlank(addressWorkds)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分
                    if(addressWorkds.length()>=4){
                        sorce = sorce +1;
                        isAddress = true;
                    }
                }


                BillboardTalentRelated related = new BillboardTalentRelated();
                related.setBillboardId(billboardId);
                related.setSStar(Double.valueOf(sorce>5?5:sorce));
                related.setTalentId(s.getId());
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
            }

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardTalentRelated::getSStar).reversed());
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
    public List<BillboardTalentRelatedResponse> getTalentRecommend(Long billboardId) {
        return billboardTalentRelatedMapper.getTalentRecommend(billboardId);
    }

    @Override
    public List<HarvestBillboardRelatedDTO> getBillboardRecommendByTalentId(Long id) {
        return billboardTalentRelatedMapper.getBillboardRecommendByTalentId(id);
    }

    @Override
    public List<HavestCollaborateDTO> getCollaborateByTalentId(Long id) {
        return billboardTalentRelatedMapper.getCollaborateByTalentId(id);
    }

    @Override
    public List<MyBillboradCollaborateResponse> getMyBillboradCollaborate(Long talentId) {
        List<MyBillboradCollaborateResponse> responses =  billboardTalentRelatedMapper.getMyBillboradCollaborate(talentId);
        return responses;
    }

    @Override
    public List<RelateTalentDTO> getRelatedTalentByBillboardId(Long id) {
        List<RelateTalentDTO> relateTalents = billboardTalentRelatedMapper.getRelatedTalentByBillboardId(id);
        if(CollectionUtils.isEmpty(relateTalents) || relateTalents.get(0) == null){
            Billboard billboard = billboardService.getById(id);
            if(billboard != null){
                String[] keys = billboard.getTechKeys().split(";");
                if(keys.length == 0 || keys.length == 1){
                   String[] keys2 = billboard.getTechKeys().split(",");
                   if(keys2.length > 1){
                       keys = keys2;
                   }
                   if(keys[0].indexOf("，")>0){
                       String[] keys3 = billboard.getTechKeys().split("，");
                       keys = keys3;
                   }
                }

                LambdaQueryWrapper<TalentPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.like(TalentPool::getQueryKeys, keys[0]);
                lambdaQueryWrapper.eq(TalentPool::getStatus, AuditingStatusEnum.PASS.getCode());
                lambdaQueryWrapper.last("LIMIT 1");

                List<TalentPool> talentPools = talentPoolMapper.selectList(lambdaQueryWrapper);
                if(talentPools == null || talentPools.size()<1){
                    LambdaQueryWrapper<TalentPool> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(TalentPool::getStatus, AuditingStatusEnum.PASS.getCode());
                    lambdaQueryWrapper1.orderByDesc(TalentPool::getCreateAt);
                    lambdaQueryWrapper1.last("LIMIT 1");
                     talentPools = talentPoolMapper.selectList(lambdaQueryWrapper1);
                }

                if(CollectionUtils.isNotEmpty(talentPools)){
                    List<RelateTalentDTO> list = mapperFacade.mapAsList(talentPools, RelateTalentDTO.class);

                    return list;
                }
            }
        }

        return relateTalents;
    }

    @Override
    public void deleteByTalentId(List<Long> list) {
        LambdaQueryWrapper<BillboardTalentRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardTalentRelated::getTalentId, list);

        billboardTalentRelatedMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void deleteByBillboardId(List<Long> list) {
        LambdaQueryWrapper<BillboardTalentRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardTalentRelated::getBillboardId, list);

        billboardTalentRelatedMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void addTalentRelatedByTalentId(Long id) {
        TalentPool talentPool = talentPoolService.getById(id);

        LambdaQueryWrapper<BillboardTalentRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardTalentRelated::getTalentId, id);
        lambdaQueryWrapper.eq(BillboardTalentRelated::getStatus, 0);
        billboardTalentRelatedMapper.delete(lambdaQueryWrapper);

        // 获取一百条
        LambdaQueryWrapper<Billboard> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode())
                .eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode())
                .orderByDesc(Billboard::getCreateAt)
                .last("LIMIT 100");

        List<Billboard> billboards = billboardService.list(queryWrapper);


        if(billboards != null && billboards.size()>0){
            // 分数
            List<BillboardTalentRelated> relateds = new ArrayList<>();
            List<BillboardTalentRelated> finalRelateds = relateds;

            for(int i=0; i<billboards.size(); i++){
                int sorce = 0;
                String researchDirection = talentPool.getResearchDirection();
                researchDirection = StrCommon.clear(researchDirection);

                Billboard s = billboards.get(i);

                // 研究方向 ===== 榜单名称
                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(researchDirection, s.getTitle());
                sameWords = StrCommon.getResult(sameWords);


                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配3个字以下 0 分，匹配3个字给1分， 匹配4个字以上给2分
                    if(sameWords.length()>=6 && sameWords.length() <8){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=8){
                        sorce = sorce +2;
                    }
                }


                // 技术关键词 === 研究成果
                String techKeys = s.getTechKeys();
                techKeys = StrCommon.clear(techKeys);
                String resultFiled = talentPool.getResearchDirection() + talentPool.getRemark();
                resultFiled = StrCommon.clear(resultFiled);

                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, resultFiled);
                techWords = StrCommon.getResult(techWords);

                if(StrUtil.isNotBlank(techWords)){
                    double num = ComputeSimilarityRatio.SimilarDegree(techKeys, talentPool.getResearchDirection());
                    // 如果匹配1个字以下 0 分，匹配44个字给2分， 匹配5个字以上给4分， 匹配6个字以上给5分
                    if(techWords.length()>=6){
                        sorce = sorce +5;
                    }else if(techWords.length()>=5){
                        sorce = sorce +4;
                    }else if(techWords.length()>=4){
                        sorce = sorce +2;
                    }


                    // 行业  === 个人简介
                    Integer categories = s.getCategories();
                    DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                    if(dictionaryDTO != null){
                        String categoriesName = dictionaryDTO.getDicValue();
                        if(StrUtil.isNotBlank(categoriesName) && StrUtil.isNotBlank(resultFiled)){
                            String techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, resultFiled);
                            techSameWorkds = StrCommon.getResult(techSameWorkds);

                            if(StrUtil.isNotBlank(techSameWorkds)){
                                // 如果匹配2个字以上2分，匹配2个字给1分，其它 0分
                                if(techSameWorkds.length()>=5){
                                    sorce = sorce +2;
                                }else if(techSameWorkds.length() ==4){
                                    sorce = sorce +1;
                                }
                            }
                        }
                    }


                    /**
                     *  地区 === 地区
                     */
                    // 榜单发布地区
                    boolean isAddress = false;
                    String billboradAddress = s.getProvinceName()+s.getCityName()+s.getAreaName();
                    // 帅才地区
                    String address = talentPool.getProvinceName()+talentPool.getCityName()+talentPool.getAreaName();
                    // 匹配地区
                    String addressWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(billboradAddress, address);
                    addressWorkds = StrCommon.getAddress(addressWorkds);

                    if(StrUtil.isNotBlank(addressWorkds)){
                        // 如果匹配2个字以下 0 分，匹配2个字给1分
                        if(addressWorkds.length()>=4){
                            sorce = sorce +1;
                            isAddress = true;
                        }
                    }


                    BillboardTalentRelated related = new BillboardTalentRelated();
                    related.setBillboardId(s.getId());
                    related.setSStar(Double.valueOf(sorce>5?5:sorce));
                    related.setTalentId(talentPool.getId());
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
            }

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardTalentRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            if(CollectionUtils.isNotEmpty(relateds)){
                this.saveBatch(relateds);
            }
        }

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











}
