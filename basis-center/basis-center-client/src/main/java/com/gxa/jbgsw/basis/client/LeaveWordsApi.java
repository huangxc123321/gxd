package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/8/4 0004 15:56
 * @Version 2.0
 */
public interface LeaveWordsApi {

    @PostMapping(value = "/leavewords/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping(value = "/leavewords/add")
    public void add(@RequestBody LeaveWordsDTO leaveWordsDTO) throws BizException;

    @PostMapping("/leavewords/pageQuery")
    PageResult<LeaveWordsResponse> pageQuery(@RequestBody LeaveWordsRequest request);

    @GetMapping("/leavewords/getLeaveWordsById")
    public LeaveWordsResponse getLeaveWordsById(@RequestParam("id") Long id) throws BizException;

    @PostMapping("/leavewords/replay")
    public void replay(@RequestBody LeaveWordsDTO leaveWordsDTO) throws BizException;

}
