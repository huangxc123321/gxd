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
 * @Date 2023/7/18 0018 8:46
 * @Version 2.0
 */
public interface ShareCommunityApi {

    @PostMapping("/share/community/add")
    void add(@RequestBody ShareCommunityDTO shareCommunityDTO);

    @PostMapping("/share/community/update")
    void update(@RequestBody ShareCommunityDTO shareCommunityDTO);

    @PostMapping("/share/community/updateStatus")
    void updateStatus(@RequestBody ShareCommunityAuditDTO shareCommunityAuditDTO);

    @PostMapping(value = "/share/community/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/share/community/detail")
    ShareCommunityDTO detail(@RequestParam("id") Long id);

    @PostMapping("/share/community/pageQuery")
    PageResult<ShareCommunityResponse> pageQuery(@RequestBody ShareCommunityRequest request);

}
