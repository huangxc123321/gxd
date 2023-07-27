package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CommentDTO;
import com.gxa.jbgsw.business.protocol.dto.CommentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/11 0011 17:53
 * @Version 2.0
 */
public interface ShareCommentApi {

    @PostMapping("/comment/add")
    void add(@RequestBody CommentDTO commentDTO);

    @GetMapping("/comment/getCommentById")
    List<CommentResponse> getCommentById(@RequestParam(value = "shareId") Long shareId, @RequestParam(value = "parentId") Long parentId);

}
