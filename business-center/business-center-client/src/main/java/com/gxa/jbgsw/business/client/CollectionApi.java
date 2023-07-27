package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.CollectionDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/7/25 0025 18:40
 * @Version 2.0
 */
public interface CollectionApi {

    @PostMapping("/collection/add")
    void add(@RequestBody CollectionDTO collectionDTO);

    @PostMapping("/collection/delete")
    void delete(@RequestBody CollectionDTO collectionDTO);

    @GetMapping("/collection/getCollection")
    CollectionDTO getCollection(@RequestParam("id") Long id, @RequestParam("userId") Long userId, @RequestParam("type") Integer type);

    @GetMapping("/collection/queryMyCollections")
    MyCollectionResponse queryMyCollections(@RequestBody MyCollectionRequest myCollectionRequest);

    @GetMapping("/collection/deleteById")
    void deleteById(@RequestParam("id") Long id);

    @GetMapping("/collection/getCollections")
    Integer getCollections(@RequestParam("userId") Long userId);
}
