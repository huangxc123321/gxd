package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface AttentionApi {
    @PostMapping("/attention/queryMyAttentions")
    MyAttentionResponse queryMyAttentions(@RequestBody MyAttentionRequest myAttentionRequest);
}
