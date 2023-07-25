package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.business.client.CollectionApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "collectionApi", configuration = {KeepErrMsgConfiguration.class})
public interface CollectionFeignApi extends CollectionApi {
}
