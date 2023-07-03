package com.gxa.jbgsw.user.service;


import com.gxa.jbgsw.user.protocol.dto.LoginRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;

public interface LoginService {
    UserResponse login(LoginRequest loginRequest);
}
