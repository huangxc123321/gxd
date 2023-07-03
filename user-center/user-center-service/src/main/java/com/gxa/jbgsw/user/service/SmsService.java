package com.gxa.jbgsw.user.service;

import com.gxa.jbgsw.user.protocol.dto.SmsMessageDTO;

/**
 * @Author Mr. huang
 * @Date 2023/6/22 0022 17:23
 * @Version 2.0
 */
public interface SmsService {
    int send(SmsMessageDTO smsMessage) throws Exception;
}
