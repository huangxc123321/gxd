package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.LikesDTO;
import com.gxa.jbgsw.business.protocol.dto.LikesResponse;
import com.gxa.jbgsw.business.protocol.dto.MessageDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/8/23 0023 10:46
 * @Version 2.0
 */
public interface MyLikesApi {

    @PostMapping("/likes/add")
    void add(@RequestBody LikesDTO likesDTO);

    @GetMapping(value = "/likes/delete")
    void delete(@RequestParam("id") Long id);

    @GetMapping(value = "/likes/getByPid")
    LikesResponse getByPid(@RequestParam("pid") Long pid);

}
