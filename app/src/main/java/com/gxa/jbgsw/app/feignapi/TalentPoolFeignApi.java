package com.gxa.jbgsw.app.feignapi;


import com.gxa.jbgsw.business.client.TalentPoolApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "talentPoolApi", configuration = {KeepErrMsgConfiguration.class})
public interface TalentPoolFeignApi extends TalentPoolApi {
}
