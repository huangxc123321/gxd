package com.gxa.jbgsw.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.jbgsw.business.entity.ShareComment;
import com.gxa.jbgsw.business.entity.ShareCommunity;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/11 0011 17:37
 * @Version 2.0
 */
public interface ShareCommentMapper extends BaseMapper<ShareComment> {
    List<CommentResponse> getCommentById(@Param("shareId") Long shareId);
}
