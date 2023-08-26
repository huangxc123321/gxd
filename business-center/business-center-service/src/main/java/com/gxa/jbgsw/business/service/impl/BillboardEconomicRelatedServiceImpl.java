package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.feignapi.UserFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardEconomicRelatedMapper;
import com.gxa.jbgsw.business.protocol.dto.AppRequiresAccepptDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardEconomicRelatedResponse;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardEconomicRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.TechEconomicManService;
import com.gxa.jbgsw.common.utils.ComputeSimilarityRatio;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
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
 * 榜单与经纪人的匹配关联表 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-07-20
 */
@Service
public class BillboardEconomicRelatedServiceImpl extends ServiceImpl<BillboardEconomicRelatedMapper, BillboardEconomicRelated> implements BillboardEconomicRelatedService {
    @Resource
    TechEconomicManService techEconomicManService;
    @Resource
    BillboardService billboardService;
    @Resource
    BillboardEconomicRelatedMapper billboardEconomicRelatedMapper;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void addEconomicRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // TODO: 2023/7/20 0020 还有一些条件，后期加上
        List<TechEconomicMan> economics = techEconomicManService.list();
        if(economics != null && economics.size()>0){
            // 分数
            AtomicReference<Integer> sorce = new AtomicReference<>(0);
            List<BillboardEconomicRelated> relateds = new ArrayList<>();
            List<BillboardEconomicRelated> finalRelateds = relateds;
            economics.stream().forEach(s->{
                String title = billboard.getTitle();

                // 榜单名称 === 项目经历
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

                // 技术关键词 === 专业标签
                String techKeys = billboard.getTechKeys();
                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys, s.getLabel());
                double num = ComputeSimilarityRatio.SimilarDegree(techKeys,s.getLabel());
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


                // 行业 === 技术领域
                Integer categories = billboard.getCategories();
                // 行业名称
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                    String mwords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, s.getLabel());
                    // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                    if(mwords.length()>=2 && mwords.length() <3){
                        sorce.set(sorce.get().intValue() + 1);
                    }else if(mwords.length()>=3){
                        sorce.set(sorce.get().intValue() + 2);
                    }else{
                        sorce.set(sorce.get().intValue() + 0);
                    }
                }

                // 榜单发布地区 === 经纪人地区
                String cityName = billboard.getCityName();
                // 经纪人地区
                String unitName = s.getCityName();
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

                BillboardEconomicRelated related = new BillboardEconomicRelated();
                related.setBillboardId(billboardId);
                related.setSStar(c.doubleValue());
                related.setEconomicId(s.getId());
                related.setCreateAt(new Date());

                UserDTO userDTO = userFeignApi.getUserByMobile(s.getMobile());
                if(userDTO != null){
                    related.setEconomicUserId(userDTO.getId());
                }

                finalRelateds.add(related);
            });

            // 排序，选出分最多的十条
            relateds.stream().sorted(Comparator.comparing(BillboardEconomicRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }
            // 批量保存
            this.saveBatch(relateds);
        }


    }

    @Override
    public List<BillboardEconomicRelatedResponse> getEconomicRecommend(Long billboardId) {
        return billboardEconomicRelatedMapper.getEconomicRecommend(billboardId);
    }

    @Override
    public BillboardEconomicRelated getMyEconomicMan(Long billboardId) {
        LambdaQueryWrapper<BillboardEconomicRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardEconomicRelated::getBillboardId, billboardId);
        lambdaQueryWrapper.ge(BillboardEconomicRelated::getHStart, 0);
        lambdaQueryWrapper.last("limit 1");

        List<BillboardEconomicRelated> relateds = billboardEconomicRelatedMapper.selectList(lambdaQueryWrapper);
        if(relateds != null && relateds.size()>0){
            return relateds.get(0);
        }

        return null;
    }

    @Override
    public void updateRequireStatus(AppRequiresAccepptDTO requiresAccepptDTO) {
        LambdaUpdateWrapper<BillboardEconomicRelated> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(BillboardEconomicRelated::getStatus, requiresAccepptDTO.getStatus())
                           .set(BillboardEconomicRelated::getRemark, requiresAccepptDTO.getRemark())
                           .eq(BillboardEconomicRelated::getId, requiresAccepptDTO.getId());
        billboardEconomicRelatedMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void deleteByEconomicId(List<Long> list) {
        LambdaQueryWrapper<BillboardEconomicRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardEconomicRelated::getEconomicId, list);

        billboardEconomicRelatedMapper.delete(lambdaQueryWrapper);
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
