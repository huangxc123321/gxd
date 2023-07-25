package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.CollectionDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Mr. huang
 * @Date 2023/7/25 0025 18:40
 * @Version 2.0
 */
public interface CollectionApi {

    @PostMapping("/collection/add")
    void add(@RequestBody CollectionDTO collectionDTO);

}
