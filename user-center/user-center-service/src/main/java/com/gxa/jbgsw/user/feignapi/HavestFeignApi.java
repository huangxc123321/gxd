package com.gxa.jbgsw.user.feignapi;

import com.gxa.jbgsw.business.client.HarvestApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "harvestApi")
public interface HavestFeignApi extends HarvestApi {
}
