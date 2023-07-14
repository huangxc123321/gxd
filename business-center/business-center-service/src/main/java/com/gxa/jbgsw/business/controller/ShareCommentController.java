package com.gxa.jbgsw.business.controller;

import com.gxa.jbgsw.business.client.ShareCommentApi;
import com.gxa.jbgsw.business.entity.ShareComment;
import com.gxa.jbgsw.business.protocol.dto.CommentDTO;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import com.gxa.jbgsw.business.service.ShareCommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "评论管理")
public class ShareCommentController implements ShareCommentApi {
    @Resource
    ShareCommentService shareCommentService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(CommentDTO commentDTO) {
        ShareComment shareComment = mapperFacade.map(commentDTO, ShareComment.class);
        shareCommentService.save(shareComment);
    }

    @Override
    public List<CommentResponse> getCommentById(Long id) {
        return shareCommentService.getCommentById(id);
    }


}
