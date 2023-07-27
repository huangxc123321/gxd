package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.ShareCommunityFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "用户中心: 我的成果")
@RestController
@Slf4j
@ResponseBody
public class MyShareCommunityController extends BaseController {
    @Resource
    ShareCommunityFeignApi shareCommunityFeignApi;


    @ApiOperation("我要分享")
    @PostMapping("/my/share/community/add")
    void add(@RequestBody ShareCommunityDTO shareCommunityDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        shareCommunityDTO.setCreateBy(userId);
        // 分享文章或者视频状态默认值： 0 待审核  1 已审核  2 未通过
        shareCommunityDTO.setStatus(0);
        shareCommunityDTO.setCreateAt(new Date());
        shareCommunityDTO.setNick(this.getUserNick());

        shareCommunityFeignApi.add(shareCommunityDTO);
    }


    @ApiOperation("获取我的分享列表")
    @PostMapping("/my/share/community/getMyShareCommunityPages")
    PageResult<MyShareCommunityResponse> getMyShareCommunityPages(@RequestBody MyShareCommunityRequest request){
        return shareCommunityFeignApi.getMyShareCommunityPages(request);
    }

    @ApiOperation(value = "批量删除我的分享", notes = "批量删除我的分享")
    @PostMapping("/my/share/community/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        shareCommunityFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("获取我的分享文章或者视频的详情")
    @GetMapping("/my/share/community/detail")
    ShareCommunityDetailDTO detail(@RequestParam("id") Long id){
        return shareCommunityFeignApi.detail(id);
    }

    @ApiOperation("点赞")
    @GetMapping("/my/share/community/addlikes")
    void addlikes(@RequestParam("id") Long id) throws BizException {
        shareCommunityFeignApi.addlikes(id);
    }

}