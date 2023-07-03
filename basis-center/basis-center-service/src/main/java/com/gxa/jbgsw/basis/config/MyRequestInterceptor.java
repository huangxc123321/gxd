package com.gxa.jbgsw.basis.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
@Slf4j
public class MyRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("MyRequestInterceptor apply begin.");
        try {
            String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
            if (null != sessionId) {
                requestTemplate.header("Cookie", "SESSION=" + sessionId);
            }
        } catch (Exception e) {
            log.error("MyRequestInterceptor exception: ", e);
        }
    }
}
