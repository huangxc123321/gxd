package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyCollectionResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.CollectionFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户中心: 我的收藏")
@RestController
@Slf4j
@ResponseBody
public class MyCollectionController extends BaseController {
    @Resource
    CollectionFeignApi collectionFeignApi;

    @ApiOperation("获取我的收藏")
    @PostMapping("/collection/pageQuery")
    MyCollectionResponse pageQuery(@RequestBody MyCollectionRequest myCollectionRequest){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        myCollectionRequest.setCreateBy(userId);
        MyCollectionResponse response = collectionFeignApi.queryMyCollections(myCollectionRequest);

        return response;
    }

    @ApiOperation(value = "取消收藏", notes = "取消收藏: 传收藏ID")
    @GetMapping("/collection/deleteById")
    public void deleteById(@RequestParam("id") Long id){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        collectionFeignApi.deleteById(id);
    }

}
