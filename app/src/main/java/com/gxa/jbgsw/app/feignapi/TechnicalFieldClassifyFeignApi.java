package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.TechnicalFieldClassifyApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "technicalFieldClassifyApi", configuration = {KeepErrMsgConfiguration.class})
public interface TechnicalFieldClassifyFeignApi extends TechnicalFieldClassifyApi {
}
