package com.gxa.jbgsw.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.app.feignapi.ShareCommentFeignApi;
import com.gxa.jbgsw.business.protocol.dto.CommentDTO;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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


    List<CommentResponse> getCommentById() throws BizException{
        
        return null;
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
