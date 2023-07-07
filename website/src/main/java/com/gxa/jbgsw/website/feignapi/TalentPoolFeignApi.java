package com.gxa.jbgsw.website.feignapi;


import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.TalentPoolApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "talentPoolApi", configuration = {KeepErrMsgConfiguration.class})
public interface TalentPoolFeignApi extends TalentPoolApi {
}
