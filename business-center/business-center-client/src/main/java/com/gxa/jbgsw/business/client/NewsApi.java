package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 11:16
 * @Version 2.0
 */
public interface NewsApi {

    @PostMapping("/new/add")
    void add(@RequestBody NewsDTO newsDTO);

    @PostMapping(value = "/new/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/new/pageQuery")
    PageResult<NewsResponse> pageQuery(@RequestBody NewsRequest request);

    @PostMapping("/new/update")
    void update(@RequestBody NewsDTO newsDTO);

    @GetMapping("/new/detail")
    NewsDTO detail(@RequestParam("id") Long id);



}
