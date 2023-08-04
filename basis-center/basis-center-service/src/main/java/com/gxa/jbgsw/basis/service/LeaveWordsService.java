package com.gxa.jbgsw.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.entity.LeaveWords;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsRequest;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsResponse;
import com.gxa.jbgsw.common.utils.PageResult;

/**
 * @Author Mr. huang
 * @Date 2023/8/4 0004 16:11
 * @Version 2.0
 */
public interface LeaveWordsService extends IService<LeaveWords> {
    void deleteBatchIds(Long[] ids);

    PageResult<LeaveWordsResponse> pageQuery(LeaveWordsRequest request);
}
