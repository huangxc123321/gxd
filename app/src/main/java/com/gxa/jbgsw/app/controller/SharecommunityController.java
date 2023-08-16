package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.business.protocol.dto.CommunityResponse;
import com.gxa.jbgsw.business.protocol.dto.ShareCommuntiyRequest;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.app.feignapi.ShareCommunityFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "分享社区")
@RestController
@Slf4j
public class SharecommunityController extends BaseController {
    @Resource
    ShareCommunityFeignApi shareCommunityFeignApi;


    @ApiOperation("获取分享列表")
    @PostMapping("/share/communtiy/getShareItems")
    PageResult<CommunityResponse> getShareItems(@RequestBody ShareCommuntiyRequest request){
        return shareCommunityFeignApi.getShareItems(request);
    }


    @ApiOperation("热门分享")
    @GetMapping("/share/communtiy/getHotShare")
    List<CommunityResponse> getHotShare(){
        return shareCommunityFeignApi.getHotShare();
    }



}
