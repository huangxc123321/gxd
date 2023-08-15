package com.gxa.jbgsw.basis.controller;

import com.gxa.jbgsw.basis.client.LeaveWordsApi;
import com.gxa.jbgsw.basis.entity.Banner;
import com.gxa.jbgsw.basis.entity.LeaveWords;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsDTO;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsReplayDTO;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsRequest;
import com.gxa.jbgsw.basis.protocol.dto.LeaveWordsResponse;
import com.gxa.jbgsw.basis.protocol.enums.LeaveWordsReplayEnum;
import com.gxa.jbgsw.basis.service.LeaveWordsService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api("留言管理")
public class LeaveWordsController implements LeaveWordsApi {
    @Resource
    LeaveWordsService leaveWordsService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        leaveWordsService.deleteBatchIds(ids);
    }

    @Override
    public void add(LeaveWordsDTO leaveWordsDTO) throws BizException {
        LeaveWords leaveWords = mapperFacade.map(leaveWordsDTO, LeaveWords.class);
        leaveWordsService.save(leaveWords);
    }

    @Override
    public PageResult<LeaveWordsResponse> pageQuery(LeaveWordsRequest request) {
        return leaveWordsService.pageQuery(request);
    }

    @Override
    public LeaveWordsResponse getLeaveWordsById(Long id) throws BizException {
        LeaveWords leaveWords = leaveWordsService.getById(id);
        LeaveWordsResponse response = mapperFacade.map(leaveWords, LeaveWordsResponse.class);

        if(response != null){
            response.setReplyName(LeaveWordsReplayEnum.getNameByIndex(response.getReply()));
        }

        return response;
    }

    @Override
    public void replay(LeaveWordsReplayDTO leaveWordsReplayDTO) throws BizException {
        LeaveWords leaveWords = leaveWordsService.getById(leaveWordsReplayDTO.getId());

        leaveWords.setReply(LeaveWordsReplayEnum.SUCESS.getCode());
        BeanUtils.copyProperties(leaveWordsReplayDTO, leaveWords, CopyPropertionIngoreNull.getNullPropertyNames(leaveWords));
        leaveWordsService.saveOrUpdate(leaveWords);
    }
}
