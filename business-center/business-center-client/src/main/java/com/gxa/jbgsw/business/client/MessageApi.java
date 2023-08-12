package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageApi {

    @PostMapping("/message/add")
    void add(@RequestBody MessageDTO messageDTO);

    @GetMapping("/message/getMessages")
    Integer getMessages(@RequestParam("userId") Long userId, @RequestParam("type") Integer type);

    @PostMapping("/message/queryMessages")
    MyMessagePageResult queryMessages(@RequestBody MyMessageRequest myMessageRequest);

    @GetMapping("/message/getAllMessages")
    Integer getAllMessages(@RequestParam("userId") Long userId);

    @PostMapping(value = "/message/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/message/updateReadFlag")
    void updateReadFlag(@RequestParam("id") Long id);

    @PostMapping("/message/getMyMessages")
    AppMessageResponse getMyMessages(@RequestBody AppMessageRequest appMessageRequest);

    @GetMapping("/message/updateAllReadFlag")
    void updateAllReadFlag(@RequestParam("userId") Long userId, @RequestParam("type") Integer type);
}
