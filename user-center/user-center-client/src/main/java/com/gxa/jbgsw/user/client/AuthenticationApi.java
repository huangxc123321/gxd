package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.user.protocol.dto.AuthenticationDTO;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/6/26 0026 14:47
 * @Version 2.0
 */
public interface AuthenticationApi {

    @PostMapping("/authentication/add")
    void add(@RequestBody AuthenticationDTO authenticationDTO);

    @GetMapping("/authentication/getAuthInfoByUserId")
    AuthenticationDTO getAuthInfoByUserId(@RequestParam("userId") Long userId);

}
