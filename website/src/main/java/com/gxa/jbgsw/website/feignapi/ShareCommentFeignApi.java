package com.gxa.jbgsw.website.feignapi;


import com.gxa.jbgsw.business.client.ShareCommentApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "shareCommentApi", configuration = {KeepErrMsgConfiguration.class})
public interface ShareCommentFeignApi extends ShareCommentApi {
}
