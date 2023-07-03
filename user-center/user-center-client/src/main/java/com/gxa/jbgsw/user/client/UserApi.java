package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserApi {

    @GetMapping("/user/getUserById")
    UserResponse getUserById(@RequestParam("id") Long id);

    @GetMapping("/user/getUserByCode")
    UserResponse getUserByCode(@RequestParam("code") String code);

    @PostMapping(value = "/device/alarm/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/user/pageQuery")
    PageResult<UserResponse> pageQuery(@RequestBody UserRequest request);

    @PostMapping("/user/add")
    void add(@RequestBody UserDTO userDTO);

    @GetMapping("/user/update/useStatus")
    void updateUseStatus(@RequestParam("id")Long id, @RequestParam("useStauts")Integer useStauts);

    @PostMapping("/user/update")
    void update(@RequestBody UserDTO userDTO);
}

