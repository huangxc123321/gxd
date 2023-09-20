package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.protocol.dto.HotSearchWordResponse;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/9/20 0020 8:47
 * @Version 2.0
 */
public interface HotSearchWordService {
    void add(String searchFiled);

    List<HotSearchWordResponse> getHotSearchWords();
}
