package com.gxa.jbgsw.business.listener;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.business.mapper.NewsMapper;
import com.gxa.jbgsw.business.protocol.enums.NewsStatusEnum;
import com.gxa.jbgsw.business.service.NewsService;
import com.gxa.jbgsw.common.utils.RedisKeys;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听所有db的过期事件
 *
 * @author huangxc
 */
@Component
public class RedisKeyExpiration extends KeyExpirationEventMessageListener {
    @Resource
    NewsService newsService;


    public RedisKeyExpiration(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对 redis 数据失效事件，进行数据处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 获取到失效的 key，进行取消订单业务处理
        String expiredKey = message.toString();

        // 新闻定时执行的前缀
        String newsPublishTimePrefixKey = RedisKeys.NEWS_PUBLIS_TIME;

        if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, newsPublishTimePrefixKey)){
            // 新闻定时发布
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            String id = strs[strs.length-1];
            // 状态：0 发布， 1 待发布
            newsService.updateStatus(id, NewsStatusEnum.PUBLISHED.getCode());
        }



    }

}
