package com.gxa.jbgsw.business.listener;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.mapper.NewsMapper;
import com.gxa.jbgsw.business.protocol.enums.MessageOriginEnum;
import com.gxa.jbgsw.business.protocol.enums.MessageTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.NewsStatusEnum;
import com.gxa.jbgsw.business.service.*;
import com.gxa.jbgsw.common.utils.RedisKeys;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 监听所有db的过期事件
 *
 * @author huangxc
 */
@Component
public class RedisKeyExpiration extends KeyExpirationEventMessageListener {
    @Resource
    NewsService newsService;
    @Resource
    BillboardService billboardService;
    @Resource
    BillboardHarvestRelatedService billboardHarvestRelatedService;
    @Resource
    BillboardEconomicRelatedService billboardEconomicRelatedService;
    @Resource
    BillboardTalentRelatedService billboardTalentRelatedService;
    @Resource
    MessageService messageService;


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
        // 榜单成果、帅才、经纪人匹配推荐处理
        String billboardRelatedRecommendPrefixKey = RedisKeys.BILLBOARD_RELATED_RECOMMEND_TASK;
        // 成果匹配榜单
        String havestRelatedRecommendPrefixKey = RedisKeys.HARVEST_RELATED_RECOMMEND_TASK;
        // 经纪人榜单匹配
        String economictRelatedRecommendPrefixKey = RedisKeys.ECONOMIC_RELATED_RECOMMEND_TASK;
        // 帅才榜单匹配
        String talentRelatedRecommendPrefixKey = RedisKeys.TALENT_RELATED_RECOMMEND_TASK;

        if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, newsPublishTimePrefixKey)){
            // 新闻定时发布
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            String id = strs[strs.length-1];
            // 状态：0 发布， 1 待发布
            newsService.updateStatus(id, NewsStatusEnum.PUBLISHED.getCode());
        }
        else if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, havestRelatedRecommendPrefixKey)){
            // 成果匹配榜单
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            // 榜单ID
            String id = strs[strs.length-1];
            System.out.println("开始定时任务");

            // 匹配榜单
            billboardHarvestRelatedService.addBillboardRelated(Long.valueOf(id));
        }
        else if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, talentRelatedRecommendPrefixKey)){
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            // 榜单ID
            String id = strs[strs.length-1];

            // 帅才匹配榜单
            billboardTalentRelatedService.addTalentRelatedByTalentId(Long.valueOf(id));
        }

        // 经纪人匹配榜单
        else if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, economictRelatedRecommendPrefixKey)){
            // 经纪人ID
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            // 榜单ID
            String id = strs[strs.length-1];
            System.out.println("开始定时任务");

            // 经纪人匹配匹配榜单
            billboardEconomicRelatedService.addEconomicRelatedByEcomicId(Long.valueOf(id));
        }

        else if(expiredKey != null && StrUtil.startWithIgnoreCase(expiredKey, billboardRelatedRecommendPrefixKey)){
            // 榜单成果、帅才、经纪人匹配推荐处理
            String[] strs = StrUtil.splitToArray(expiredKey, StrUtil.C_COLON);
            // 榜单ID
            String id = strs[strs.length-1];
            // 根据匹配规则，生成成果、帅才、经纪人与榜单的关联关系表
            System.out.println("开始定时任务");
            billboardHarvestRelatedService.addHarvestRelated(Long.valueOf(id));
            billboardTalentRelatedService.addTalentRelated(Long.valueOf(id));
            billboardEconomicRelatedService.addEconomicRelated(Long.valueOf(id));
            System.out.println("结束定时任务");

            // 写系统消息
            com.gxa.jbgsw.business.entity.Message msg = new com.gxa.jbgsw.business.entity.Message();
            Billboard billboard = billboardService.getById(Long.valueOf(id));
            if(billboard != null){
                msg.setUserId(billboard.getCreateBy());
                msg.setOrigin(MessageOriginEnum.APPLY.getCode());
                if(billboard != null){
                    msg.setTitle(billboard.getTitle()+"榜单推荐通知");
                    msg.setPid(billboard.getId());
                }else{
                    msg.setTitle("榜单推荐通知");
                }
                msg.setType(MessageTypeEnum.SYS.getCode());
                msg.setCreateAt(new Date());
                msg.setReadFlag(0);

                messageService.save(msg);
            }

        }



    }

}
