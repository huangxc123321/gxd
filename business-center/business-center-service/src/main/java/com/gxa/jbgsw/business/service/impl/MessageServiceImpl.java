package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.mapper.MessageMapper;
import com.gxa.jbgsw.business.protocol.dto.AppMessageRequest;
import com.gxa.jbgsw.business.protocol.dto.AppMessageResponse;
import com.gxa.jbgsw.business.protocol.dto.MessageDTO;
import com.gxa.jbgsw.business.protocol.dto.MyMessageRequest;
import com.gxa.jbgsw.business.service.MessageService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
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
    public AppMessageResponse pageQUery(AppMessageRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getUserId, request.getUserId());
        // 所有
        if(request.getType() == 1){
            lambdaQueryWrapper.eq(Message::getReadFlag, 0);
        }
        lambdaQueryWrapper.orderByDesc(Message::getCreateAt);

        List<Message> messages =  messageMapper.selectList(lambdaQueryWrapper);

        boolean noReadFlage = false;
        if(request.getType() == 0){
            for(Message m : messages){
                if(m.getReadFlag().equals(0)){
                    noReadFlage = true;
                    break;
                }
            }
        }

        PageInfo<Message> pageInfo = new PageInfo<>(messages);

        //类型转换
        AppMessageResponse appMessageResponse = new AppMessageResponse();
        appMessageResponse.setAllIsNoReadFlag(noReadFlage);
        if(pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size()>0){
            List<MessageDTO> list = mapperFacade.mapAsList(messages, MessageDTO.class);
            appMessageResponse.setMessages(list);
        }
        appMessageResponse.setNoReadFlag(noReadFlage);
        appMessageResponse.setPageNum(pageInfo.getPageNum());
        appMessageResponse.setPages(pageInfo.getPages());
        appMessageResponse.setPageSize(pageInfo.getPageSize());
        appMessageResponse.setTotal(pageInfo.getTotal());

        // 获取是否有新需求单





        return null;
    }
}
