package com.gxa.jbgsw.basis.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.gxa.jbgsw.basis.mapper.TechnicalFieldClassifyMapper;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.service.InitDataService;
import com.gxa.jbgsw.common.utils.RedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class InitDataServiceImpl implements InitDataService {
    @Resource
    TechnicalFieldClassifyMapper technicalFieldClassifyMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    @PostConstruct
    public void getAll() {
        System.out.println("开始初始化数据");


        List<TechnicalFieldClassifyDTO> pos =  technicalFieldClassifyMapper.getAll(-1L);
        String json = JSONArray.toJSONString(pos);
        stringRedisTemplate.opsForValue().set(RedisKeys.TECH_DOMAIN_VALUE, json);

        System.out.println("结束初始化数据");
    }
}
