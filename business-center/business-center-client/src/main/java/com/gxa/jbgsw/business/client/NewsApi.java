package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 11:16
 * @Version 2.0
 */
public interface NewsApi {

    @PostMapping("/news/add")
    void add(@RequestBody NewsDTO newsDTO);

    @PostMapping(value = "/news/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/news/pageQuery")
    PageResult<NewsResponse> pageQuery(@RequestBody NewsRequest request);

    @PostMapping("/news/update")
    void update(@RequestBody NewsDTO newsDTO);

    @GetMapping("/news/detail")
    NewsDTO detail(@RequestParam("id") Long id);

    @GetMapping("/news/getHotNews")
    List<NewsResponse> getHotNews();
}
