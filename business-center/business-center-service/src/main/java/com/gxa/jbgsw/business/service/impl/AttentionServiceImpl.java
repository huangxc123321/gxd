package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.AttentionMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AttentionTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.AttentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 我的关注 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {
    @Resource
    AttentionMapper attentionMapper;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public MyAttentionResponse queryMyAttentions(MyAttentionRequest myAttentionRequest) {
        MyAttentionResponse response = new MyAttentionResponse();

        // 获取该类型下的分页数据
        PageHelper.startPage(myAttentionRequest.getPageNum(), myAttentionRequest.getPageSize());

        // 先统计另外类型的关注数量
        if(AttentionTypeEnum.GOV.getCode().equals(myAttentionRequest.getType())){
            Integer govNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.GOV.getCode());
            Integer buzNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.BUZ.getCode());
            Integer talentNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.TALENT.getCode());
            response.setGovNum(govNum);
            response.setBusBuzNum(buzNum);
            response.setTalentNum(talentNum);

            List<MyAttentionInfo> attentionInfos = attentionMapper.getMyAttentionInfos(myAttentionRequest);
            response.setBillboards(attentionInfos);

        }
        else if(AttentionTypeEnum.BUZ.getCode().equals(myAttentionRequest.getType())){
            Integer govNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.GOV.getCode());
            Integer buzNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.BUZ.getCode());
            Integer talentNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.TALENT.getCode());
            response.setGovNum(govNum);
            response.setBusBuzNum(buzNum);
            response.setTalentNum(talentNum);

            List<MyAttentionInfo> attentionInfos = attentionMapper.getMyBuzAttentionInfos(myAttentionRequest);
            response.setBillboards(attentionInfos);

        } else if(AttentionTypeEnum.TALENT.getCode().equals(myAttentionRequest.getType())){
            Integer govNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.GOV.getCode());
            Integer buzNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.BUZ.getCode());
            Integer talentNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.TALENT.getCode());
            response.setGovNum(govNum);
            response.setBusBuzNum(buzNum);
            response.setTalentNum(talentNum);

            List<MyAttentionInfo> attentionInfos = attentionMapper.getMyAttentionTalentInfos(myAttentionRequest);
            if(CollectionUtils.isNotEmpty(attentionInfos)){
                attentionInfos.stream().forEach(s->{
                    DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                    if(dictionaryDTO != null){
                        s.setProfessionalName(dictionaryDTO.getDicValue());
                    }
                });
            }
            response.setBillboards(attentionInfos);
        }

        return response;
    }

    @Override
    public void deleteAttention(Long pid, Long userId, Integer type) {
        LambdaQueryWrapper<Attention> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Attention::getPid, pid)
                          .eq(Attention::getUserId, userId)
                          .eq(Attention::getType, type);

        attentionMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public AttentionDTO getAttention(Long pid, Long userId, Integer attentionType) {
        LambdaQueryWrapper<Attention> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Attention::getPid, pid)
                .eq(Attention::getUserId, userId)
                .eq(Attention::getType, attentionType)
                .last("limit 1");
        List<Attention> attentions = attentionMapper.selectList(lambdaQueryWrapper);
        if(attentions != null && attentions.size()>0){
            Attention attention = attentions.get(0);
            AttentionDTO attentionDTO = mapperFacade.map(attention, AttentionDTO.class);

            return attentionDTO;
        }

        return null;
    }

    @Override
    public Integer getAttentionNum(Long userId) {
        QueryWrapper<Attention> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                    .eq("user_id", userId);

        Integer count = attentionMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public List<AttentionDynamicDTO> getDynamicInfo(Long userId) {
        return attentionMapper.getDynamicInfo(userId);
    }

    Integer getAttentionNum(Long userId, Integer type){
        QueryWrapper<Attention> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("type", type)
                .eq("user_id", userId);

        Integer count = attentionMapper.selectCount(queryWrapper);
        return count;
    }



}
