package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import com.gxa.jbgsw.business.protocol.dto.HotSearchWordResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/9/20 0020 9:01
 * @Version 2.0
 */
public interface HotSearchWordsApi {

    @GetMapping("/hot/getHotSearchWords")
    List<HotSearchWordResponse> getHotSearchWords();

}
