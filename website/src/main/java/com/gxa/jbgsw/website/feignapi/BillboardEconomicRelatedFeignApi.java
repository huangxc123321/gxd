package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.BillboardEconomicRelatedApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "business-center-service", contextId = "billboardEconomicRelatedApi", configuration = {KeepErrMsgConfiguration.class})
public interface BillboardEconomicRelatedFeignApi extends BillboardEconomicRelatedApi {
}
