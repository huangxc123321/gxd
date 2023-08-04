package com.gxa.jbgsw.website.feignapi;

import com.gxa.jbgsw.website.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.LeaveWordsApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "leaveWordsApi", configuration = {KeepErrMsgConfiguration.class})
public interface LeaveWordsFeignApi extends LeaveWordsApi {
}
