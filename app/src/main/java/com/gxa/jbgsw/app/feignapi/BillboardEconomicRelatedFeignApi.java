package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.business.client.BillboardEconomicRelatedApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardEconomicRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardEconomicRelatedFeignApi extends BillboardEconomicRelatedApi {
}
