package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.NewsApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "newsApi", configuration = {KeepErrMsgConfiguration.class})
public interface NewsFeignApi extends NewsApi {
}
