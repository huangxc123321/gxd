package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.mapper.BillboardTalentRelatedMapper;
import com.gxa.jbgsw.business.protocol.dto.BillboardTalentRelatedResponse;
import com.gxa.jbgsw.business.protocol.dto.HarvestBillboardRelatedDTO;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.BillboardTalentRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.gxa.jbgsw.common.utils.ComputeSimilarityRatio;
import com.gxa.jbgsw.common.utils.RedisKeys;
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
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void addTalentRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // 获取一百条
        // TODO: 2023/7/20 0020 还有一些条件，后期加上
        List<TalentPool> talentPools = talentPoolService.list();
        if(talentPools != null && talentPools.size()>0){
            // 分数
            AtomicReference<Integer> sorce = new AtomicReference<>(0);
            List<BillboardTalentRelated> relateds = new ArrayList<>();
            List<BillboardTalentRelated> finalRelateds = relateds;
            talentPools.stream().forEach(s->{
                String title = billboard.getTitle();

                // 榜单名称 ===== 研究方向
                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, s.getResearchDirection());
                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                    if(sameWords.length()>=2 && sameWords.length() <3){
                        sorce.set(sorce.get().intValue() + 1);
                    }else if(sameWords.length()>=3){
                        sorce.set(sorce.get().intValue() + 2);
                    }else{
                        sorce.set(sorce.get().intValue() + 0);
                    }
                }


                // 技术关键词 === 研究成果
                String techKeys = billboard.getTechKeys();
                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, s.getProject());
                double num = ComputeSimilarityRatio.SimilarDegree(techKeys,s.getTechDomain());
                if(StrUtil.isNotBlank(techWords)){
                    // 如果匹配1个字以下 0 分，匹配1个字给1分， 匹配2个字以上给2分
                    if(sameWords.length()>=3 && num > 0.15){
                        sorce.set(sorce.get().intValue() + 5);
                    }else if(sameWords.length()>=3 && num < 0.15 && num >= 0.1){
                        sorce.set(sorce.get().intValue() + 4);
                    }else if(sameWords.length()>=2){
                        sorce.set(sorce.get().intValue() + 2);
                    }else{
                        sorce.set(sorce.get().intValue() + 0);
                    }
                }


                // 行业  === 技术领域
                Integer categories = billboard.getCategories();
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                    String techDomain =  s.getTechDomain();
                    String techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, techDomain);
                    if(StrUtil.isNotBlank(techSameWorkds)){
                        // 如果匹配2个字以上2分，匹配2个字给1分，其它 0分
                        if(techSameWorkds.length()>=2){
                            sorce.set(sorce.get().intValue() + 2);
                        }else if(techSameWorkds.length() ==2){
                            sorce.set(sorce.get().intValue() + 1);
                        }else{
                            sorce.set(sorce.get().intValue() + 0);
                        }
                    }
                }


                /**
                 *  地区 === 地区
                 */
                // 榜单发布地区
                String cityName = billboard.getCityName();
                // 帅才地区
                String address = s.getCityName();
                // 匹配地区
                String cityWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(cityName, address);
                if(StrUtil.isNotBlank(cityWords)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分
                    if(sameWords.length()>=2){
                        sorce.set(sorce.get().intValue() + 1);
                    }else{
                        sorce.set(sorce.get().intValue() + 0);
                    }
                }

                BigDecimal a = new BigDecimal(sorce.get());
                BigDecimal b = new BigDecimal(2);
                BigDecimal c = a.divide(b, 2, RoundingMode.UP);

                BillboardTalentRelated related = new BillboardTalentRelated();
                related.setBillboardId(billboardId);
                related.setSStar(c.doubleValue());
                related.setTalentId(s.getId());
                related.setCreateAt(new Date());
                finalRelateds.add(related);
            });

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardTalentRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            this.saveBatch(relateds);
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
