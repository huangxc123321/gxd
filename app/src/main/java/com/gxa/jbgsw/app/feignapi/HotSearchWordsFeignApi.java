package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.HotSearchWordsApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "hotSearchWordsApi", configuration = {KeepErrMsgConfiguration.class})
public interface HotSearchWordsFeignApi extends HotSearchWordsApi {
}
