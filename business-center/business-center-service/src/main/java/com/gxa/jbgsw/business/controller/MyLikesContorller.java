package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.MyLikesApi;
import com.gxa.jbgsw.business.protocol.dto.LikesDTO;
import com.gxa.jbgsw.business.protocol.dto.LikesResponse;
import com.gxa.jbgsw.business.service.MyLikesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "我的点赞")
public class MyLikesContorller implements MyLikesApi {
    @Resource
    MyLikesService myLikesService;

    @Override
    public void add(LikesDTO likesDTO) {
        myLikesService.add(likesDTO);
    }

    @Override
    public void delete(Long id) {
        myLikesService.delete(id);
    }

    @Override
    public LikesResponse getByPid(Long pid) {
        return myLikesService.getByPid(pid);
    }
}
