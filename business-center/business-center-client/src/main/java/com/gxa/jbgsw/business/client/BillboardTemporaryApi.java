package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BillboardTemporaryApi {

    @PostMapping("/temporary/billboard/add")
    void add(@RequestBody BillboardTemporaryDTO billboardTemporaryDTO);

    @PostMapping(value = "/temporary/billboard/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/temporary/billboard/getById")
    BillboardTemporaryDTO getById(@RequestParam("id") Long id);

    @PostMapping("/temporary/billboard/update")
    void update(@RequestBody BillboardTemporaryDTO billboardTemporaryDTO);

    @PostMapping("/temporary/billboard/pageQuery")
    PageResult<BillboardTemporaryResponse> pageQuery(@RequestBody BillboardTemporaryRequest request);

    @PostMapping(value = "/temporary/billboard/batchInsert",consumes = MediaType.APPLICATION_JSON_VALUE)
    void batchInsert(@RequestBody BillboardTemporaryDTO[] batchList);

    @GetMapping("/temporary/billboard/deleteByCreateBy")
    void deleteByCreateBy(@RequestParam("userId") Long userId);
}
