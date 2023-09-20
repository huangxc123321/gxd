package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.HotSearchWordsApi;
import com.gxa.jbgsw.business.protocol.dto.HotSearchWordResponse;
import com.gxa.jbgsw.business.service.HotSearchWordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "热搜词管理")
public class HotSearchWordsController implements HotSearchWordsApi {
    @Resource
    HotSearchWordService hotSearchWordService;

    @Override
    public List<HotSearchWordResponse> getHotSearchWords() {
        return hotSearchWordService.getHotSearchWords();
    }
}
