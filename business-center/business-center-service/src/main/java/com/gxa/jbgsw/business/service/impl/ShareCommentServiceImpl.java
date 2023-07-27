package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.entity.ShareComment;
import com.gxa.jbgsw.business.mapper.ShareCommentMapper;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import com.gxa.jbgsw.business.service.ShareCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShareCommentServiceImpl  extends ServiceImpl<ShareCommentMapper, ShareComment> implements ShareCommentService {
    @Resource
    ShareCommentMapper shareCommentMapper;

    @Override
    public List<CommentResponse> getCommentById(Long id) {
        List<CommentResponse> commentResponses =shareCommentMapper.getCommentById(id);
        return commentResponses;
    }




}
