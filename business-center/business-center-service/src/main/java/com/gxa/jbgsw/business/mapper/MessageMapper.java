package com.gxa.jbgsw.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.protocol.dto.AppMessageRequest;
import com.gxa.jbgsw.business.protocol.dto.AppMessageResponse;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/28 0028 13:52
 * @Version 2.0
 */
public interface MessageMapper extends BaseMapper<Message> {
    List<AppMessageResponse> pageQUery(AppMessageRequest request);
}
