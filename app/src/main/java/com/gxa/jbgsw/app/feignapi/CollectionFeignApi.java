package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.CollectionApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "collectionApi", configuration = {KeepErrMsgConfiguration.class})
public interface CollectionFeignApi extends CollectionApi {
}
