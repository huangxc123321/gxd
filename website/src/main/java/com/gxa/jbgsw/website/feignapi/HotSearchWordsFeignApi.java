package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.HotSearchWordsApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "hotSearchWordsApi", configuration = {KeepErrMsgConfiguration.class})
public interface HotSearchWordsFeignApi extends HotSearchWordsApi {
}
