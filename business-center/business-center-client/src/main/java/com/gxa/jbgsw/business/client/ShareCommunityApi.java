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
    ShareCommunityDetailDTO detail(@RequestParam("id") Long id);

    @PostMapping("/share/community/pageQuery")
    PageResult<ShareCommunityResponse> pageQuery(@RequestBody ShareCommunityRequest request);

    @PostMapping("/share/community/getMyShareCommunityPages")
    PageResult<MyShareCommunityResponse> getMyShareCommunityPages(@RequestBody MyShareCommunityRequest request);

    @GetMapping("/my/share/community/addViews")
    void addViews(@RequestParam("id") Long id);

    @GetMapping("/my/share/community/addlikes")
    void addlikes(@RequestParam("id") Long id);

    @GetMapping("/my/share/community/addComments")
    void addComments(@RequestParam("id") Long id);

    @GetMapping("/my/share/community/getShareCommunitys")
    Integer getShareCommunitys(@RequestParam("userId") Long userId);
}
