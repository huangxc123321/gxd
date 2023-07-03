package com.gxa.jbgsw.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class OrderNoGenerater {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 编号前缀
     */
    public enum OrderNoTypeEnum {
        //工单前缀
        WORKORDER("GD"),
        ;
        private String prefix;
        private OrderNoTypeEnum(String prefix) {
            this.prefix = prefix;
        }
    }

    /**
     * 生成工单编号
     *
     * @param orderNoType
     * @return
     */
    public String generate(OrderNoTypeEnum orderNoType) {
        return generate(orderNoType, 0);
    }

    private String generate(OrderNoTypeEnum orderNoType, int count) {
        if (count > 50) {
            return "";
        }

        String randomStr = RandomStringUtils.randomNumeric(4);
        String dateStr = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String key = orderNoType+"-"+dateStr+"-"+ randomStr;
        if(stringRedisTemplate.hasKey(key)){
            return generate(orderNoType, ++count);
        }

        //设置值和过期时间
        stringRedisTemplate.opsForValue().set(key, randomStr);
        stringRedisTemplate.expire(key, 24*3600, TimeUnit.SECONDS);

        String no = "";
        no += orderNoType.prefix + dateStr + randomStr;
        return no;
    }

}
