package com.gxa.jbgsw.admin.feignapi;


import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.TalentPoolApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "business-center-service", contextId = "talentPoolApi", configuration = {KeepErrMsgConfiguration.class})
public interface TalentPoolFeignApi extends TalentPoolApi {
}
