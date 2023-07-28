package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.MessageDTO;
import com.gxa.jbgsw.business.protocol.dto.MyMessagePageResult;
import com.gxa.jbgsw.business.protocol.dto.MyMessageRequest;
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

    @PostMapping(value = "/billboard/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/message/updateReadFlag")
    void updateReadFlag(@RequestParam("id") Long id);
}
