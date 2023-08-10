package com.gxa.jbgsw.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.protocol.dto.AppMessageRequest;
import com.gxa.jbgsw.business.protocol.dto.AppMessageRequiresDTO;
import com.gxa.jbgsw.business.protocol.dto.AppMessageResponse;
import com.gxa.jbgsw.business.protocol.dto.MessageDTO;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/28 0028 13:52
 * @Version 2.0
 */
public interface MessageMapper extends BaseMapper<Message> {
    List<AppMessageRequiresDTO> getMyMessagesForRequires(AppMessageRequest appMessageRequest);

    List<MessageDTO> getMyAllMessages(AppMessageRequest appMessageRequest);
}
