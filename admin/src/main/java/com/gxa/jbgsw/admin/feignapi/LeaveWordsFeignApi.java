package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.LeaveWordsApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "leaveWordsApi", configuration = {KeepErrMsgConfiguration.class})
public interface LeaveWordsFeignApi extends LeaveWordsApi {
}
