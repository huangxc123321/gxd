package com.gxa.jbgsw.app.controller;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsDTO;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.app.feignapi.LeaveWordsFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

        if(StrUtil.isBlank(leaveWordsDTO.getContent())){
            throw new BizException(BusinessErrorCode.LEAVE_WORDS_IS_EXIST);
        }

        leaveWordsFeignApi.add(leaveWordsDTO);
    }

}
