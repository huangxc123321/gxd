package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.MyMessagePageResult;
import com.gxa.jbgsw.business.protocol.dto.MyMessageRequest;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.MessageFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "消息管理")
@RestController
@Slf4j
@ResponseBody
public class MessageController extends BaseController {
    @Resource
    MessageFeignApi messageFeignApi;

    @ApiOperation("获取消息")
    @PostMapping("/message/pageQuery")
    MyMessagePageResult pageQuery(@RequestBody MyMessageRequest myMessageRequest){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        myMessageRequest.setCreateBy(userId);
        MyMessagePageResult response = messageFeignApi.queryMessages(myMessageRequest);

        return response;
    }

    @ApiOperation(value = "批量删除消息", notes = "批量删除消息")
    @PostMapping("/message/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        messageFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation(value = "标记已读", notes = "标记已读")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "消息ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/message/updateReadFlag")
    public void updateReadFlag(@RequestParam("id")Long id){
        messageFeignApi.updateReadFlag(id);
    }




}
