package com.gxa.jbgsw.user.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Mr. huang
 * @Date 2023/6/26 0026 14:47
 * @Version 2.0
 */
public interface AuthenticationApi {

    @PostMapping(value = "/auth/info/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);


}
