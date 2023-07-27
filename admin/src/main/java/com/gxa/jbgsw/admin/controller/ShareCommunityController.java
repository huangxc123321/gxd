package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.ShareCommunityFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "分享社区管理")
@RestController
@Slf4j
@ResponseBody
public class ShareCommunityController extends BaseController {
    @Resource
    ShareCommunityFeignApi shareCommunityFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("获取分享列表")
    @PostMapping("/share/community/pageQuery")
    PageResult<ShareCommunityResponse> pageQuery(@RequestBody ShareCommunityRequest request){
        return shareCommunityFeignApi.pageQuery(request);
    }

    @ApiOperation("获取分享文章或者视频的详情")
    @GetMapping("/share/community/detail")
    ShareCommunityDetailDTO detail(@RequestParam("id") Long id){
        return shareCommunityFeignApi.detail(id);
    }

    @ApiOperation("审核分享文章或者视频")
    @PostMapping("/share/community/updateStatus")
    void updateStatus(@RequestBody ShareCommunityAuditDTO shareCommunityAuditDTO){
        shareCommunityAuditDTO.setAuditAt(new Date());
        shareCommunityAuditDTO.setAuditUserId(this.getUserId());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            shareCommunityAuditDTO.setAuditUserName(userResponse.getNick());
        }

        shareCommunityFeignApi.updateStatus(shareCommunityAuditDTO);
    }

    @ApiOperation("更新分享文章或者视频")
    @PostMapping("/share/community/update")
    void update(@RequestBody ShareCommunityDTO shareCommunityDTO) throws BizException {
        // 分享文章或者视频者
        shareCommunityFeignApi.update(shareCommunityDTO);
    }

    @ApiOperation("新增分享文章或者视频")
    @PostMapping("/share/community/add")
    void add(@RequestBody ShareCommunityDTO shareCommunityDTO) throws BizException {
        shareCommunityDTO.setCreateBy(this.getUserId());
        // 分享文章或者视频状态默认值： 0 待审核  1 已审核  2 未通过
        shareCommunityDTO.setStatus(0);
        shareCommunityDTO.setCreateAt(new Date());
        // 分享文章或者视频者
        UserResponse userResponse = getUser();
        if(userResponse != null){
            shareCommunityDTO.setNick(userResponse.getNick());
        }
        
        shareCommunityFeignApi.add(shareCommunityDTO);
    }

    @ApiOperation(value = "批量删除分享文章或视频", notes = "批量删除分享文章或视频")
    @PostMapping("/share/community/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        shareCommunityFeignApi.deleteBatchIds(ids);
    }

    private UserResponse getUser(){
        Long userId = this.getUserId();
        String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isBlank(userInfo)){
            throw new BizException(UserErrorCode.LOGIN_CODE_ERROR);
        }
        UserResponse userResponse = JSONObject.parseObject(userInfo, UserResponse.class);
        return userResponse;
    }



}
