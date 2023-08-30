package com.gxa.jbgsw.common.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangxc
 * @Description： PeelingMachine-CMS-Server
 * @Date： 2020/10/19 15:29
 */

@Slf4j
public class BaseController {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    public BaseController() {
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected Long getUserId() {
        Long userId = null;

        String token = this.getRequest().getHeader("token");
        if(StrUtil.isNotBlank(token)){
            String id = stringRedisTemplate.opsForValue().get(RedisKeys.USER_TOKEN+token);
            if(StrUtil.isNotBlank(id)){
                userId = Long.valueOf(id);
            }
        }

        return userId;
    }

    protected Long getDepartmentId() {
        Long departmentId = null;
        Long userId = getUserId();
        String json = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isNotBlank(json)){
            // 去掉转意符
            Object object = JSON.parse(json);
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            departmentId = jsonObject.getLong("departmentId");
        }
        return departmentId;
    }

    protected List<Long> getMeAndSonDepartmentIds(Long departmentId) {
        List<Long> result = new ArrayList<>();
        String key = RedisKeys.DEPRTMENT_INFO + departmentId;
        String ids = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isNotBlank(ids)){
            List<String> idsList = StrUtil.split(ids, StrUtil.C_COMMA);
            idsList.stream().forEach(s -> {
                result.add(Long.valueOf(s));
            });
        }
        return result;
    }

    protected String getUserNick(){
        Long userId = this.getUserId();
        String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isNotBlank(userInfo)){
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            if(jsonObject != null){
                return  jsonObject.getString("nick");
            }
        }
        return "";
    }

    protected String getUnitName(){
        Long userId = this.getUserId();
        String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isNotBlank(userInfo)){
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            if(jsonObject != null){
                return  jsonObject.getString("unitName");
            }
        }
        return "";
    }

}
