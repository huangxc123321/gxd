package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.NewsApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "newsApi", configuration = {KeepErrMsgConfiguration.class})
public interface NewsFeignApi extends NewsApi {
}
