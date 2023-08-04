package com.gxa.jbgsw.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.basis.entity.LeaveWords;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsRequest;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/8/4 0004 16:12
 * @Version 2.0
 */
public interface LeaveWordsMapper extends BaseMapper<LeaveWords> {
    List<LeaveWords> pageQuery(LeaveWordsRequest request);
}
