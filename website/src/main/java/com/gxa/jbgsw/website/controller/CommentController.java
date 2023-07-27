package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.protocol.dto.CommentDTO;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.ShareCommentFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "评论管理")
@RestController
@Slf4j
@ResponseBody
public class CommentController extends BaseController {
    @Resource
    ShareCommentFeignApi shareCommentFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @ApiOperation("根据分享文章/视频ID获取评论")
    @GetMapping("/comment/getCommentById")
    ApiResult<List<CommentResponse>> getCommentById(@RequestParam("shareId") Long shareId,@RequestParam("parentId") Long parentId) throws BizException{
        List<CommentResponse> commentResponses = shareCommentFeignApi.getCommentById(shareId, parentId);

        ApiResult<List<CommentResponse>> apiResult = new ApiResult();
        apiResult.setData(commentResponses);

        return apiResult;
    }

    @ApiOperation("新增评论信息")
    @PostMapping("/comment/add")
    void add(@RequestBody CommentDTO commentDTO) throws BizException {
        Long userId = this.getUserId();
        commentDTO.setCreateAt(new Date());
        commentDTO.setUserId(userId);
        String key = RedisKeys.USER_INFO+userId;
        Object userReponse = stringRedisTemplate.opsForValue().get(key);
        if(userReponse == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        UserResponse user = JSONObject.parseObject(userReponse.toString(), UserResponse.class);
        commentDTO.setAvatar(user.getAvatar());
        commentDTO.setNick(user.getNick());

        shareCommentFeignApi.add(commentDTO);
    }


}
