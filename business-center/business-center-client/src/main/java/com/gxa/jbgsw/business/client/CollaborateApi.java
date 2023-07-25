package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mac on 2023/7/24.
 */
public interface CollaborateApi {
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO);
}
