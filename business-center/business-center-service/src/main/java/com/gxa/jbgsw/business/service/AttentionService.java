package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Attention;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.AttentionDTO;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;

/**
 * <p>
 * 我的关注 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface AttentionService extends IService<Attention> {

    MyAttentionResponse queryMyAttentions(MyAttentionRequest myAttentionRequest);

    void deleteAttention(Long pid, Long userId, Integer type);

    AttentionDTO getAttention(Long pid, Long userId, Integer attentionType);

    Integer getAttentionNum(Long userId);
}
