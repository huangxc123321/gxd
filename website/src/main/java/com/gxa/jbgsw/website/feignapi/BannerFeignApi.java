package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.basis.client.BannerApi;
import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "basis-center-service", contextId = "bannerApi", configuration = {KeepErrMsgConfiguration.class})
public interface BannerFeignApi extends BannerApi {
}
