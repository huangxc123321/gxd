package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.HarvestApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "harvestApi", configuration = {KeepErrMsgConfiguration.class})
public interface HavestFeignApi extends HarvestApi {
}
