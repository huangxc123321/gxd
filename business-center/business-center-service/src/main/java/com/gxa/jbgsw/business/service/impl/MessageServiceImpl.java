package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.BillboardEconomicRelated;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.mapper.MessageMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardEconomicRelatedStatusEnum;
import com.gxa.jbgsw.business.service.MessageService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    MessageMapper messageMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public Integer getMessages(Long userId, Integer type) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("user_id", userId)
                .eq("type", type);

        Integer count = messageMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public Integer getAllMessages(Long userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("user_id", userId);

        Integer count = messageMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public PageResult<MessageDTO> queryMessages(MyMessageRequest myMessageRequest) {
        PageHelper.startPage(myMessageRequest.getPageNum(), myMessageRequest.getPageSize());

        LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getUserId, myMessageRequest.getCreateBy())
                          .eq(Message::getType, myMessageRequest.getType())
                          .orderByDesc(Message::getCreateAt);

        List<Message> messages =  messageMapper.selectList(lambdaQueryWrapper);
        PageInfo<Message> pageInfo = new PageInfo<>(messages);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<Message>>() {
        }.build(), new TypeBuilder<PageResult<MessageDTO>>() {}.build());
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        messageMapper.deleteBatchIds(list);
    }

    @Override
    public void updateReadFlag(Long id) {
        LambdaUpdateWrapper<Message> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Message::getReadFlag, 1)
                           .eq(Message::getId, id);

        messageMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public AppMessageResponse getMyMessages(AppMessageRequest appMessageRequest) {
        AppMessageResponse response = new AppMessageResponse();
        PageHelper.startPage(appMessageRequest.getPageNum(), appMessageRequest.getPageSize());

       if(appMessageRequest.getType().equals(2)){
           boolean requiresNoReadFlag = false;
           // 需求单
           List<AppMessageRequiresDTO> requires =  messageMapper.getMyMessagesForRequires(appMessageRequest);
           if(CollectionUtils.isNotEmpty(requires)){
               for(int i=0; i<requires.size(); i++){
                   requires.get(i).setStatusName(BillboardEconomicRelatedStatusEnum.getNameByIndex(requires.get(i).getStatus()));
                   if(requires.get(i).getReadFlag().equals(0)){
                       requiresNoReadFlag = true;
                       break;
                   }
               }
               response.setRequiresNoReadFlag(requiresNoReadFlag);
           }
           PageInfo<AppMessageRequiresDTO> pageInfo = new PageInfo<>(requires);
           response.setRequires(requires);
           response.setTotal(pageInfo.getTotal());
           response.setPageSize(pageInfo.getPageSize());
           response.setPages(pageInfo.getPages());
           response.setPageNum(pageInfo.getPageNum());
           response.setSize(pageInfo.getSize());

           // 判断消息是否有未读的
           LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
           lambdaQueryWrapper.eq(Message::getUserId, appMessageRequest.getCreateBy());
           // 未读
           lambdaQueryWrapper.eq(Message::getReadFlag, 0);
           // 系统消息
           lambdaQueryWrapper.eq(Message::getType, 0);
           lambdaQueryWrapper.orderByDesc(Message::getCreateAt);
           List<Message> messages =  messageMapper.selectList(lambdaQueryWrapper);
           if(messages != null && messages.size()>0){
               response.setNoReadFlag(true);
               response.setAllIsNoReadFlag(true);
           }
       }else {
           // 消息
           if(appMessageRequest.getType().equals(0)){
               // 所有消息
               boolean noReadFlag = false;
               List<MessageDTO> messages =  messageMapper.getMyAllMessages(appMessageRequest);
               if(CollectionUtils.isNotEmpty(messages)){
                   for(int i=0; i<messages.size(); i++) {
                       if(messages.get(i).getReadFlag().equals(0)){
                           noReadFlag = true;
                           break;
                       }
                   }
               }
               response.setNoReadFlag(noReadFlag);
               response.setAllIsNoReadFlag(noReadFlag);

               PageInfo<MessageDTO> pageInfo = new PageInfo<>(messages);
               response.setMessages(messages);
               response.setTotal(pageInfo.getTotal());
               response.setPageSize(pageInfo.getPageSize());
               response.setPages(pageInfo.getPages());
               response.setPageNum(pageInfo.getPageNum());
               response.setSize(pageInfo.getSize());


               // 判断需求是否有未读的
               LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
               lambdaQueryWrapper.eq(Message::getUserId, appMessageRequest.getCreateBy());
               // 未读
               lambdaQueryWrapper.eq(Message::getReadFlag, 0);
               // 需求消息
               lambdaQueryWrapper.eq(Message::getType, 2);
               lambdaQueryWrapper.orderByDesc(Message::getCreateAt);
               List<Message> reqMessages =  messageMapper.selectList(lambdaQueryWrapper);
               if(reqMessages != null && reqMessages.size()>0){
                   response.setRequiresNoReadFlag(true);
               }
           }
           // 未读
           else if(appMessageRequest.getType().equals(1)){
                   // 所有消息
                   boolean noReadFlag = false;
                   List<MessageDTO> messages =  messageMapper.getMyAllMessages(appMessageRequest);
                   if(CollectionUtils.isNotEmpty(messages)){
                       for(int i=0; i<messages.size(); i++) {
                           if(messages.get(i).getReadFlag().equals(0)){
                               noReadFlag = true;
                               break;
                           }
                       }
                   }
                   response.setNoReadFlag(noReadFlag);
                   response.setAllIsNoReadFlag(noReadFlag);

                   PageInfo<MessageDTO> pageInfo = new PageInfo<>(messages);
                   response.setMessages(messages);
                   response.setTotal(pageInfo.getTotal());
                   response.setPageSize(pageInfo.getPageSize());
                   response.setPages(pageInfo.getPages());
                   response.setPageNum(pageInfo.getPageNum());
                   response.setSize(pageInfo.getSize());


                   // 判断需求是否有未读的
                   LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                   lambdaQueryWrapper.eq(Message::getUserId, appMessageRequest.getCreateBy());
                   // 未读
                   lambdaQueryWrapper.eq(Message::getReadFlag, 0);
                   // 需求消息
                   lambdaQueryWrapper.eq(Message::getType, 2);
                   lambdaQueryWrapper.orderByDesc(Message::getCreateAt);
                   List<Message> reqMessages =  messageMapper.selectList(lambdaQueryWrapper);
                   if(reqMessages != null && reqMessages.size()>0){
                       response.setRequiresNoReadFlag(true);
                   }
               }
       }


        return response;
    }

    @Override
    public void updateAllReadFlag(Long userId, Integer type) {
        LambdaUpdateWrapper<Message> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Message::getReadFlag, 1)
                           .eq(Message::getUserId, userId)
                           .eq(Message::getType, type);
        messageMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public boolean getIsHaveNoRead(Long userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("user_id", userId)
                .eq("read_flag", 0);

        Integer count = messageMapper.selectCount(queryWrapper);

        if(count == null || count.intValue() ==0){
            return false;
        }else {
            return true;
        }
    }
}
