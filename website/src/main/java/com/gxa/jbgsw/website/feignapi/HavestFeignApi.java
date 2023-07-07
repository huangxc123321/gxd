package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.HarvestApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "harvestApi", configuration = {KeepErrMsgConfiguration.class})
public interface HavestFeignApi extends HarvestApi {
}
