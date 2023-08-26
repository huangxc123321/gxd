package com.gxa.jbgsw.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.entity.MyLikes;
import com.gxa.jbgsw.business.entity.News;
import com.gxa.jbgsw.business.protocol.dto.LikesDTO;
import com.gxa.jbgsw.business.protocol.dto.LikesResponse;

/**
 * @Author Mr. huang
 * @Date 2023/8/23 0023 10:44
 * @Version 2.0
 */
public interface MyLikesService  extends IService<MyLikes> {
    void add(LikesDTO likesDTO);

    void delete(Long id);

    LikesResponse getByPid(Long pid);

    void deleteLikes(Long userId, Long pid);
}
