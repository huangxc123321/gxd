package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.entity.HotSearchWord;
import com.gxa.jbgsw.business.mapper.HotSearchWordMapper;
import com.gxa.jbgsw.business.protocol.dto.HotSearchWordResponse;
import com.gxa.jbgsw.business.service.HotSearchWordService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotSearchWordServiceImpl extends ServiceImpl<HotSearchWordMapper, HotSearchWord> implements HotSearchWordService {
    @Resource
    HotSearchWordMapper hotSearchWordMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(String name) {
        LambdaQueryWrapper<HotSearchWord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HotSearchWord::getName, name);
        List<HotSearchWord> hotSearchWords = hotSearchWordMapper.selectList(lambdaQueryWrapper);
        if(hotSearchWords == null || hotSearchWords.size() <1){
            HotSearchWord hotSearchWord = new HotSearchWord();
            hotSearchWord.setName(name);
            hotSearchWord.setTotal(1);
            hotSearchWordMapper.insert(hotSearchWord);
        }else{
            UpdateWrapper<HotSearchWord> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("total = total +" + 1)
                    .eq("name", name);

            hotSearchWordMapper.update(null, updateWrapper);
        }
    }

    @Override
    public List<HotSearchWordResponse> getHotSearchWords() {
        List<HotSearchWordResponse> responses = new ArrayList<>();

        LambdaQueryWrapper<HotSearchWord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(HotSearchWord::getTotal);
        lambdaQueryWrapper.last(" LIMIT 3    ");

        List<HotSearchWord> hotSearchWords = hotSearchWordMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(hotSearchWords)){
            responses = mapperFacade.mapAsList(hotSearchWords, HotSearchWordResponse.class);
        }

        return responses;
    }
}
