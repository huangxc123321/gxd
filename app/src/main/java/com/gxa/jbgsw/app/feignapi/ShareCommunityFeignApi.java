package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.ShareCommunityApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "shareCommunityApi", configuration = {KeepErrMsgConfiguration.class})
public interface ShareCommunityFeignApi extends ShareCommunityApi {
}