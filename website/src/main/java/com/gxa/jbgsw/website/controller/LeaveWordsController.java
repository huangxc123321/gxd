package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.website.feignapi.LeaveWordsFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsDTO;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsRequest;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "留言管理")
@RestController
@Slf4j
@ResponseBody
public class LeaveWordsController extends BaseController {
    @Resource
    LeaveWordsFeignApi leaveWordsFeignApi;

    @ApiOperation("新增留言")
    @PostMapping("/leavewords/add")
    void add(@RequestBody LeaveWordsDTO leaveWordsDTO) throws BizException {
        String code = this.getUserNick();
        if(StrUtil.isNotBlank(code)){
            leaveWordsDTO.setCode(code);
        }
        leaveWordsDTO.setCreateAt(new Date());

        leaveWordsFeignApi.add(leaveWordsDTO);
    }

}
