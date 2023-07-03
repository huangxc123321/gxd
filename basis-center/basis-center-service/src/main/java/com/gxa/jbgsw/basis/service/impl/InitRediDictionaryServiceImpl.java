package com.gxa.jbgsw.basis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryTypeRequest;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryTypeResponse;
import com.gxa.jbgsw.basis.service.DictionaryTypeService;
import com.gxa.jbgsw.basis.service.InitRediDictionaryService;
import com.gxa.jbgsw.common.utils.RedisKeys;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author
 * basis-center 系统启动时，字典初始化load到redis
 */
@Component
public class InitRediDictionaryServiceImpl implements ApplicationRunner, InitRediDictionaryService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    DictionaryTypeService dictionaryTypeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initDictionary();
    }

    @Override
    public void initDictionary() {
        // 删除之前的
        String keys = RedisKeys.DICTIONARY_TYPE_VALUE + "*";
        stringRedisTemplate.delete(keys);

        // 重新赋值
        DictionaryTypeRequest request = new DictionaryTypeRequest();
        List<DictionaryTypeResponse> responses = dictionaryTypeService.list(request);
        responses.stream().forEach(s->{
            Map<String, Object> map = new HashMap<>();
            String code = s.getCode();
            List<DictionaryResponse> dictionaryResponses = dictionaryTypeService.getByCode(code);
            map.put(code, JSONObject.toJSONString(dictionaryResponses));
            String key = RedisKeys.DICTIONARY_TYPE_VALUE+code;
            stringRedisTemplate.opsForValue().set(key,JSONObject.toJSONString(dictionaryResponses));
        });
    }
}
