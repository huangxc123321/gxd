package com.gxa.jbgsw.app.controller;

import com.gxa.jbgsw.app.feignapi.*;
import com.gxa.jbgsw.business.protocol.dto.AttentionDynamicDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.AttentionDynamicResponse;
import com.gxa.jbgsw.user.protocol.dto.UserCenterDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户中心")
@RestController
@Slf4j
@ResponseBody
public class UserCenterController extends BaseController {
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    AttentionFeignApi attentionFeignApi;
    @Resource
    CollectionFeignApi collectionFeignApi;
    @Resource
    ShareCommunityFeignApi shareCommunityFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @ApiOperation("用户中心：用户统计信息")
    @GetMapping("/user/center/getUserInfo")
    UserCenterDTO getUserInfo() throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        UserResponse userResponse = userFeignApi.getUserById(userId);
        UserCenterDTO userCenterDTO = mapperFacade.map(userResponse, UserCenterDTO.class);

        // 我的关注
        Integer attentions = attentionFeignApi.getAttentionNum(userId);
        userCenterDTO.setAttentions(attentions);

        // 我的收藏
        Integer collectoins = collectionFeignApi.getCollections(userId);
        userCenterDTO.setCollections(collectoins);

        // 我的分享
        Integer shareCommunitys = shareCommunityFeignApi.getShareCommunitys(userId);
        userCenterDTO.setShareCommunitys(shareCommunitys);

        // 我的消息
        Integer messages = messageFeignApi.getAllMessages(userId);
        userCenterDTO.setMessages(messages);

        // 是否有未读消息
        boolean isHaveNoRead = messageFeignApi.getIsHaveNoRead(userId);
        userCenterDTO.setHaveNoRead(isHaveNoRead);

        // 我的积分
        userCenterDTO.setPoints(0);

        // 动态关注(查询一个月内的数据)
        List<AttentionDynamicDTO> dynamics = attentionFeignApi.getDynamicInfo(userId);
        if(CollectionUtils.isNotEmpty(dynamics)){
            List<AttentionDynamicResponse> responses = mapperFacade.mapAsList(dynamics, AttentionDynamicResponse.class);
            userCenterDTO.setAttentionDynamic(responses);
        }

        return userCenterDTO;
    }


}
