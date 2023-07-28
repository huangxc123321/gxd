package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.protocol.dto.MessageDTO;
import com.gxa.jbgsw.business.protocol.dto.MyMessageRequest;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/28 0028 13:52
 * @Version 2.0
 */
public interface MessageService extends IService<Message> {
    Integer getMessages(Long userId, Integer type);

    Integer getAllMessages(Long userId);

    PageResult<MessageDTO> queryMessages(MyMessageRequest myMessageRequest);

    void deleteBatchIds(Long[] ids);

    void updateReadFlag(Long id);
}
