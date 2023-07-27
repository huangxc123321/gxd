package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.AttentionDTO;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface AttentionApi {
    @PostMapping("/attention/queryMyAttentions")
    MyAttentionResponse queryMyAttentions(@RequestBody MyAttentionRequest myAttentionRequest);

    @PostMapping("/attention/add")
    void add(@RequestBody AttentionDTO attentionDTO);

    @PostMapping("/attention/delete")
    void delete(@RequestBody AttentionDTO attentionDTO);

    @GetMapping("/attention/getAttention")
    AttentionDTO getAttention(@RequestParam("pid")Long pid, @RequestParam("userId")Long userId, @RequestParam("attentionType")Integer attentionType);

    @GetMapping("/attention/getAttentionNum")
    Integer getAttentionNum(@RequestParam("userId")Long userId);
}
