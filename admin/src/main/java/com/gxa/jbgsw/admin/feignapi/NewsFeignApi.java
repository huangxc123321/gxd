package com.gxa.jbgsw.admin.feignapi;

import com.gxa.jbgsw.admin.config.KeepErrMsgConfiguration;
import com.gxa.jbgsw.business.client.NewsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huangxc
 */
@FeignClient(name = "business-center-service", contextId = "newsFeignApi", configuration = {KeepErrMsgConfiguration.class})
public interface NewsFeignApi extends NewsApi {
}
