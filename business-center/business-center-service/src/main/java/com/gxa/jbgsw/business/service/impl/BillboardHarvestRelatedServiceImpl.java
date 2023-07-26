package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryValueQueryRequest;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardHarvestRelated;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardHarvestRelatedMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardHarvestRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.HarvestService;
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

    @Override
    public void addHarvestRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // 获取一百条
        // TODO: 2023/7/20 0020 还有一些条件，后期加上
        List<Harvest> harvests = harvestService.list();
        if(harvests != null && harvests.size()>0){
            // 分数
            AtomicReference<Integer> sorce = new AtomicReference<>(0);
            List<BillboardHarvestRelated> relateds = new ArrayList<>();
            List<BillboardHarvestRelated> finalRelateds = relateds;
            harvests.stream().forEach(s->{
                String title = billboard.getTitle();
                // 匹配标题
                String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, s.getName());
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
                // 技术关键词
                String techKeys = billboard.getTechKeys();
                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, s.getTechDomain());
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
                // 行业
                Integer categories = billboard.getCategories();
                // 行业名称
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                   String categoriesName = dictionaryDTO.getDicValue();
                   String tradeType = s.getTradeType();

                }

                // 榜单发布地区
                String cityName = billboard.getCityName();
                // 成果的单位
                String unitName = s.getUnitName();
                // 匹配地区
                String cityWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(cityName, unitName);
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

                BillboardHarvestRelated related = new BillboardHarvestRelated();
                related.setBillboardId(billboardId);
                related.setSStar(c.doubleValue());
                related.setHarvestId(s.getId());
                related.setCreateAt(new Date());

                finalRelateds.add(related);
            });

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardHarvestRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            this.saveBatch(relateds);
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
