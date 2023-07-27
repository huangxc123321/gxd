package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.ShareComment;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/11 0011 17:37
 * @Version 2.0
 */
public interface ShareCommentService extends IService<ShareComment> {
    List<CommentResponse> getCommentById(Long shareId, Long parentId);
}
