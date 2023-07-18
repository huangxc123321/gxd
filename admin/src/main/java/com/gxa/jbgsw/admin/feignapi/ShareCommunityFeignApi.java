package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.ShareCommunityApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "shareCommunityApi", configuration = {KeepErrMsgConfiguration.class})
public interface ShareCommunityFeignApi extends ShareCommunityApi {
}