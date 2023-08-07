package com.gxa.jbgsw.app.feignapi;

import com.gxa.jbgsw.basis.client.LeaveWordsApi;
import com.gxa.jbgsw.app.config.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "leaveWordsApi", configuration = {KeepErrMsgConfiguration.class})
public interface LeaveWordsFeignApi extends LeaveWordsApi {
}
