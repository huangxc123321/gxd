package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.entity.MyLikes;
import com.gxa.jbgsw.business.mapper.MyLikesMapper;
import com.gxa.jbgsw.business.protocol.dto.LikesDTO;
import com.gxa.jbgsw.business.protocol.dto.LikesResponse;
import com.gxa.jbgsw.business.service.MyLikesService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MyLikesServiceImpl extends ServiceImpl<MyLikesMapper, MyLikes> implements MyLikesService {
    @Resource
    MyLikesMapper myLikesMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(LikesDTO likesDTO) {
        MyLikes myLikes = mapperFacade.map(likesDTO, MyLikes.class);
        myLikes.setCreateAt(new Date());

        myLikesMapper.insert(myLikes);
    }

    @Override
    public void delete(Long id) {
        myLikesMapper.deleteById(id);
    }

    @Override
    public LikesResponse getByPid(Long pid) {
        LambdaQueryWrapper<MyLikes> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MyLikes::getPid, pid);
        List<MyLikes> myLikes = myLikesMapper.selectList(lambdaQueryWrapper);

        if(myLikes != null && myLikes.size()>0){
            return mapperFacade.map(myLikes.get(0),LikesResponse.class);
        }

        return null;
    }

    @Override
    public void deleteLikes(Long userId, Long pid) {
        LambdaQueryWrapper<MyLikes> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MyLikes::getPid, pid)
                .eq(MyLikes::getUserId, userId);
        myLikesMapper.delete(lambdaQueryWrapper);
    }
}
