package com.gxa.jbgsw.business.controller;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.business.client.MessageApi;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.MessageTypeEnum;
import com.gxa.jbgsw.business.service.MessageService;
import com.gxa.jbgsw.common.utils.PageRequest;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "消息管理")
public class MessageController implements MessageApi {
    @Resource
    MessageService messageService;
    @Resource
    MapperFacade mapperFacade;


    @Override
    public void add(MessageDTO messageDTO) {
        messageDTO.setCreateAt(new Date());
        if(StrUtil.isBlank(messageDTO.getContent())){
            messageDTO.setContent(messageDTO.getTitle());
        }
        Message message = mapperFacade.map(messageDTO, Message.class);

        messageService.save(message);
    }

    @Override
        public Integer getMessages(Long userId, Integer type) {
        return messageService.getMessages(userId, type);
    }

    @Override
    public MyMessagePageResult queryMessages(MyMessageRequest myMessageRequest) {
        MyMessagePageResult myMessagePageResult  = new MyMessagePageResult();

        int sysNum = messageService.getMessages(myMessageRequest.getCreateBy(), MessageTypeEnum.SYS.getCode());
        int techNum = messageService.getMessages(myMessageRequest.getCreateBy(), MessageTypeEnum.TEC.getCode());

        PageResult<MessageDTO> pageRequest = messageService.queryMessages(myMessageRequest);

        if(pageRequest != null){
            myMessagePageResult.setMessages(pageRequest.getList());
            myMessagePageResult.setPageNum(pageRequest.getPageNum());
            myMessagePageResult.setPages(pageRequest.getPages());
            myMessagePageResult.setPageSize(pageRequest.getPageSize());
            myMessagePageResult.setTotal(pageRequest.getTotal());
            myMessagePageResult.setSysNum(sysNum);
            myMessagePageResult.setTecNum(techNum);
            myMessagePageResult.setSize(pageRequest.getSize());

        }

        return myMessagePageResult;
    }

    @Override
    public Integer getAllMessages(Long userId) {
        return messageService.getAllMessages(userId);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        messageService.deleteBatchIds(ids);
    }

    @Override
    public void updateReadFlag(Long id) {
        messageService.updateReadFlag(id);
    }

    @Override
    public AppMessageResponse getMyMessages(AppMessageRequest appMessageRequest) {
        return messageService.getMyMessages(appMessageRequest);
    }

    @Override
    public void updateAllReadFlag(Long userId, Integer type) {
        messageService.updateAllReadFlag(userId, type);
    }

    @Override
    public boolean getIsHaveNoRead(Long userId) {
        return messageService.getIsHaveNoRead(userId);
    }
}
