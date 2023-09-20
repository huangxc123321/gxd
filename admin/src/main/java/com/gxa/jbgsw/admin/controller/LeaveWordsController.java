package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.LeaveWordsFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsReplayDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "留言管理")
@RestController
@Slf4j
@ResponseBody
public class LeaveWordsController extends BaseController {
    @Resource
    LeaveWordsFeignApi leaveWordsFeignApi;
    @Resource
    UserFeignApi userFeignApi;

    @ApiOperation(value = "批量删除留言", notes = "批量删除留言")
    @PostMapping("/leavewords/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        leaveWordsFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("回复留言")
    @PostMapping("/leavewords/replay")
    void replay(@RequestBody LeaveWordsReplayDTO leaveWordsReplayDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        leaveWordsReplayDTO.setUpdateAt(new Date());
        leaveWordsReplayDTO.setUpdateBy(userId);

        leaveWordsFeignApi.replay(leaveWordsReplayDTO);
    }

    @ApiOperation("获取详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "留言ID", name = "id", dataType = "Long", paramType = "query")
    })
    @GetMapping("/leavewords/getLeaveWordsById")
    LeaveWordsResponse getLeaveWordsById(@RequestParam("id") Long id) throws BizException {
        return leaveWordsFeignApi.getLeaveWordsById(id);
    }

    @ApiOperation("获取留言信息列表")
    @PostMapping("/leavewords/pageQuery")
    PageResult<LeaveWordsResponse> pageQuery(@RequestBody LeaveWordsRequest request){
        PageResult<LeaveWordsResponse> pageResult = leaveWordsFeignApi.pageQuery(request);
        List<LeaveWordsResponse> response = pageResult.getList();

        if(CollectionUtils.isNotEmpty(response)){
            List<Long> ids = new ArrayList<>();
            List<Long> finalIds = ids;
            response.stream().forEach(s->{
                finalIds.add(s.getUpdateBy());
            });

            // 去重
            ids = finalIds.stream().distinct().collect(Collectors.toList());
            Long[] userIds = new Long[ids.size()];
            ids.toArray(userIds);
            List<UserResponse> userResponses = userFeignApi.getUserByIds(userIds);
            response.forEach(s->{
                if(s.getUpdateBy() != null){
                    UserResponse u = userResponses.stream()
                            .filter(user -> s.getUpdateBy().equals(user.getId()))
                            .findAny()
                            .orElse(null);
                    if(u != null){
                        s.setUpdateByName(u.getNick());
                    }
                }
            });
        }

        return pageResult;
    }

}
