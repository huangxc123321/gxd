package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.business.feignapi.UserFeignApi;
import com.gxa.jbgsw.business.mapper.BillboardEconomicRelatedMapper;
import com.gxa.jbgsw.business.protocol.dto.AppRequiresAccepptDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardEconomicRelatedResponse;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardEconomicRelatedStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardEconomicRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.TechEconomicManService;
import com.gxa.jbgsw.common.utils.ComputeSimilarityRatio;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.common.utils.StrCommon;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void addEconomicRelated(Long billboardId) {
        Billboard billboard = billboardService.getById(billboardId);

        // TODO: 2023/7/20 0020 还有一些条件，后期加上
        List<TechEconomicMan> economics = techEconomicManService.list();
        if(economics != null && economics.size()>0){

            List<BillboardEconomicRelated> relateds = new ArrayList<>();
            List<BillboardEconomicRelated> finalRelateds = relateds;

            for(int i=0; i<economics.size(); i++){
                // 分数
                int sorce = 0;
                TechEconomicMan  s = economics.get(i);

                String title = billboard.getTitle();

                // 榜单名称 === 专业标签
                String sameWords = "";
                String label = s.getLabel();
                label =  StrCommon.clear(label);

                sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, label);
                sameWords = StrCommon.getResult(sameWords);

                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                    if(sameWords.length()>=3 && sameWords.length() <4){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=4){
                        sorce = sorce +2;
                    }
                }


                // 技术关键词 === 技术领域
                String techKeys = billboard.getTechKeys();
                StringBuffer sb = new StringBuffer();
                // 技术领域
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

                // 加上标签
                sb.append(s.getLabel());

                String sbStr = sb.toString();
                sbStr = StrCommon.clear(sbStr);

                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys,sbStr);
                techWords = StrCommon.getResult(techWords);
                if(StrUtil.isNotBlank(techWords)){
                    double num = ComputeSimilarityRatio.SimilarDegree(techKeys,s.getLabel());
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


                // 行业 === 技术领域
                Integer categories = billboard.getCategories();
                // 行业名称
                DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                String techSameWorkds = "";
                if(dictionaryDTO != null){
                    String categoriesName = dictionaryDTO.getDicValue();
                    techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, sbStr);
                    techSameWorkds = StrCommon.getResult(techSameWorkds);
                    // 如果匹配2个字以下 0 分，匹配3~4个字给1分， 匹配4个字以上给2分
                    if(techSameWorkds.length()>=3 && techSameWorkds.length() <=4){
                        sorce = sorce +1;
                    }else if(techSameWorkds.length()>4){
                        sorce = sorce +2;
                    }
                }


                // 榜单发布地区
                boolean isAddress = false;
                String billboradAddress = billboard.getProvinceName()+billboard.getCityName()+billboard.getAreaName();
                // 帅才地区
                String address = s.getProvinceName()+s.getCityName()+s.getAreaName();
                // 匹配地区
                String addressWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(billboradAddress, address);
                addressWorkds = StrCommon.getAddress(addressWorkds);

                if(StrUtil.isNotBlank(addressWorkds)){
                    // 如果匹配4个字以下 0 分，匹配4个字给1分
                    if(addressWorkds.length()>=4){
                        sorce = sorce +1;
                        isAddress = true;
                    }
                }

                BillboardEconomicRelated related = new BillboardEconomicRelated();
                related.setBillboardId(billboardId);
                related.setSStar(Double.valueOf(sorce>5?5:sorce));
                related.setEconomicId(s.getId());
                related.setCreateAt(new Date());

                if(related.getSStar().intValue() > 2){
                    // 把只是匹配地区的排除，因为有些只是地区匹配，行业风牛马不相及
                    if(isAddress && related.getSStar() != 1){
                        relateds.add(related);
                    }else if(related.getSStar() >2){
                        relateds.add(related);
                    }
                }
            }

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
        List<Integer> list = new ArrayList<>();
        list.add(BillboardEconomicRelatedStatusEnum.RECOMMEND.getCode());
        list.add(BillboardEconomicRelatedStatusEnum.ACCEPT.getCode());
        lambdaQueryWrapper.in(BillboardEconomicRelated::getStatus, list);
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

    @Override
    public List<BillboardEconomicRelatedResponse> getAdminEconomicRecommend(Long billboardId) {
        return billboardEconomicRelatedMapper.getAdminEconomicRecommend(billboardId);
    }

    @Override
    public void deleteByBillboardId(List<Long> list) {
        LambdaQueryWrapper<BillboardEconomicRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BillboardEconomicRelated::getBillboardId, list);

        billboardEconomicRelatedMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void addEconomicRelatedByEcomicId(Long id) {
        TechEconomicMan techEconomicMan = techEconomicManService.getById(id);

        LambdaQueryWrapper<BillboardEconomicRelated> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardEconomicRelated::getEconomicId, id);
        List<Integer> status = new ArrayList<>();
        status.add(0);
        status.add(3);
        lambdaQueryWrapper.in(BillboardEconomicRelated::getStatus, status);
        billboardEconomicRelatedMapper.delete(lambdaQueryWrapper);

        // 获取一百条
        LambdaQueryWrapper<Billboard> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Billboard::getAuditStatus, AuditingStatusEnum.PASS.getCode())
                .eq(Billboard::getStatus, BillboardStatusEnum.WAIT.getCode())
                .orderByDesc(Billboard::getCreateAt)
                .last("LIMIT 100");
        List<Billboard> billboards = billboardService.list(queryWrapper);


        if(billboards != null && billboards.size()>0){
            List<BillboardEconomicRelated> relateds = new ArrayList<>();
            for(int i=0; i<billboards.size(); i++){
                // 分数
                int sorce = 0;
                Billboard  s = billboards.get(i);
                String title = s.getTitle();

                // 榜单名称 === 专业标签
                String sameWords = "";

                String label = techEconomicMan.getLabel();
                label = StrCommon.clear(label);

                sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(title, label);
                sameWords = StrCommon.getResult(sameWords);

                if(StrUtil.isNotBlank(sameWords)){
                    // 如果匹配2个字以下 0 分，匹配2个字给1分， 匹配3个字以上给2分
                    if(sameWords.length()>=3 && sameWords.length() <4){
                        sorce = sorce +1;
                    }else if(sameWords.length()>=4){
                        sorce = sorce +2;
                    }
                }


                // 技术关键词 === 技术领域
                String techKeys = s.getTechKeys();
                StringBuffer sb = new StringBuffer();
                // 技术领域
                TechnicalFieldClassifyDTO tfc1 = null;
                if(techEconomicMan.getTechDomain1() != null){
                    try{
                        tfc1 = getOneById(techEconomicMan.getTechDomain1());
                        if(tfc1 != null){
                            sb.append(tfc1.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain1() error");
                    }
                }

                TechnicalFieldClassifyDTO tfc2 = null;
                if(techEconomicMan.getTechDomain2() != null){
                    try{
                        tfc2 = getByPid(tfc1, techEconomicMan.getTechDomain2());
                        if(tfc2 != null){
                            sb.append(tfc2.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain2() error");
                    }
                }
                if(techEconomicMan.getTechDomain() != null){
                    try{
                        TechnicalFieldClassifyDTO tfc = getByPid(tfc2, techEconomicMan.getTechDomain());
                        if(tfc != null){
                            sb.append(tfc.getName());
                        }
                    }catch (Exception ex){
                        System.out.println("techEconomicMan.getTechDomain() error");
                    }
                }


                // 加上标签
                sb.append(techEconomicMan.getLabel());

                String sbStr = sb.toString();
                sbStr =  StrCommon.clear(sbStr);

                String techWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(techKeys,sbStr);
                techWords = StrCommon.getResult(techWords);
                if(StrUtil.isNotBlank(techWords)){
                    double num = ComputeSimilarityRatio.SimilarDegree(techKeys, sb.toString());
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


                // 行业 === 技术领域
                Integer categories = s.getCategories();
                if(categories != null){
                    // 行业名称
                    DictionaryDTO dictionaryDTO = getByCache(String.valueOf(DictionaryTypeCodeEnum.categories), categories.toString());
                    String techSameWorkds = "";
                    if(dictionaryDTO != null){
                        String categoriesName = dictionaryDTO.getDicValue();
                        if(StrUtil.isNotBlank(categoriesName) && StrUtil.isNotBlank(sbStr)){
                            techSameWorkds = ComputeSimilarityRatio.longestCommonSubstringNoOrder(categoriesName, sbStr);
                            techSameWorkds = StrCommon.getResult(techSameWorkds);

                            // 如果匹配2个字以下 0 分，匹配3~4个字给1分， 匹配4个字以上给2分
                            if(techSameWorkds.length()>=3 && techSameWorkds.length() <=4){
                                sorce = sorce +1;
                            }else if(techSameWorkds.length()>4){
                                sorce = sorce +2;
                            }
                        }
                    }
                }

                // 榜单发布地区
                boolean isAddress = false;
                String billboradAddress = s.getProvinceName()+s.getCityName()+s.getAreaName();
                // 帅才地区
                String address = techEconomicMan.getProvinceName()+techEconomicMan.getCityName()+techEconomicMan.getAreaName();
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

                if(sorce > 0){
                    BillboardEconomicRelated related = new BillboardEconomicRelated();
                    related.setBillboardId(s.getId());
                    related.setSStar(Double.valueOf(sorce>5?5:sorce));
                    related.setEconomicId(techEconomicMan.getId());
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
            relateds.stream().sorted(Comparator.comparing(BillboardEconomicRelated::getSStar).reversed());
            if(relateds.size()>10){
                relateds = relateds.subList(0,10);
            }

            // 批量保存
            if(CollectionUtils.isNotEmpty(relateds)){
                this.saveBatch(relateds);
            }
        }
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
