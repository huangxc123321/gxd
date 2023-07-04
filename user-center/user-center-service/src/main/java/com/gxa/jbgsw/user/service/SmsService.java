package com.gxa.jbgsw.user.service;

import com.gxa.jbgsw.user.protocol.dto.SmsMessageDTO;


public interface SmsService {
    int send(SmsMessageDTO smsMessage) throws Exception;
}
