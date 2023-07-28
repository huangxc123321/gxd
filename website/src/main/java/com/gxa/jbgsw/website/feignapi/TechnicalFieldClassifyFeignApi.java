package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "technicalFieldClassifyApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechnicalFieldClassifyFeignApi extends TechnicalFieldClassifyApi {
}
