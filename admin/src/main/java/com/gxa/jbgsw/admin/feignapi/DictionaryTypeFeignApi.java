package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.basis.client.DictionaryTypeApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "basis-center-service", contextId = "dictionaryTypeApi" , configuration = {KeepErrMsgConfiguration.class})
public interface DictionaryTypeFeignApi  extends DictionaryTypeApi {
}
