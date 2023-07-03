package com.gxa.jbgsw.user.client;


import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface LoginApi {

    @PostMapping("/login")
    UserResponse login(@RequestBody LoginRequest request) throws BizException;

    @GetMapping("/get/validate/code")
    String getValidateCode(@RequestParam("mobile") String mobile) throws Exception;

}
