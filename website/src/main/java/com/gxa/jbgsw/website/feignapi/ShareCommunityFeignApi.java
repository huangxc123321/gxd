package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.ShareCommunityApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "shareCommunityApi", configuration = {KeepErrMsgConfiguration.class})
public interface ShareCommunityFeignApi extends ShareCommunityApi {
}