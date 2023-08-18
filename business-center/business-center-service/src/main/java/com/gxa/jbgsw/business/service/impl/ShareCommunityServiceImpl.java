package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.Collection;
import com.gxa.jbgsw.business.entity.ShareCommunity;
import com.gxa.jbgsw.business.mapper.ShareCommunityMapper;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.service.ShareCommentService;
import com.gxa.jbgsw.business.service.ShareCommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 分享社区 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class ShareCommunityServiceImpl extends ServiceImpl<ShareCommunityMapper, ShareCommunity> implements ShareCommunityService {
    @Resource
    ShareCommunityMapper shareCommunityMapper;
    @Resource
    ShareCommentService shareCommentService;
    @Resource
    MapperFacade mapperFacade;


    @Override
    public void updateStatus(ShareCommunityAuditDTO shareCommunityAuditDTO) {
        LambdaUpdateWrapper<ShareCommunity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        lambdaUpdateWrapper.set(ShareCommunity::getAuditUserId, shareCommunityAuditDTO.getAuditUserId())
                .set(ShareCommunity::getAuditUserName, shareCommunityAuditDTO.getAuditUserName())
                .set(ShareCommunity::getAuditAt, shareCommunityAuditDTO.getAuditAt())
                .set(ShareCommunity::getStatus, shareCommunityAuditDTO.getStatus())
                .set(ShareCommunity::getReason, shareCommunityAuditDTO.getReason())
                .eq(ShareCommunity::getId, shareCommunityAuditDTO.getId());

        shareCommunityMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        shareCommunityMapper.deleteBatchIds(list);
    }

    @Override
    public ShareCommunityDetailDTO detail(Long id) {
        ShareCommunity shareCommunity = this.getById(id);
        ShareCommunityDetailDTO shareCommunityDTO = mapperFacade.map(shareCommunity, ShareCommunityDetailDTO.class);
        List<CommentResponse> commentResponses =  shareCommentService.getCommentById(id, -1L);
        shareCommunityDTO.setCommentResponses(commentResponses);

        return shareCommunityDTO;
    }

    @Override
    public PageResult<ShareCommunity> pageQuery(ShareCommunityRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<ShareCommunity> shareCommunities = shareCommunityMapper.pageQuery(request);
        PageInfo<ShareCommunity> pageInfo = new PageInfo<>(shareCommunities);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<ShareCommunity>>() {
        }.build(), new TypeBuilder<PageResult<ShareCommunity>>() {}.build());
    }

    @Override
    public PageResult<ShareCommunity> getMyShareCommunityPages(MyShareCommunityRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<ShareCommunity> shareCommunities = shareCommunityMapper.getMyShareCommunityPages(request);
        PageInfo<ShareCommunity> pageInfo = new PageInfo<>(shareCommunities);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<ShareCommunity>>() {
        }.build(), new TypeBuilder<PageResult<ShareCommunity>>() {}.build());
    }

    @Override
    public void addlikes(Long id) {
        LambdaUpdateWrapper<ShareCommunity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ShareCommunity::getId, id)
                           .setSql(" likes = likes + 1");

        shareCommunityMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void addViews(Long id) {
        LambdaUpdateWrapper<ShareCommunity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ShareCommunity::getId, id)
                .setSql(" views = views + 1");

        shareCommunityMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void addComments(Long id) {
        LambdaUpdateWrapper<ShareCommunity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ShareCommunity::getId, id)
                .setSql(" comments = comments + 1");

        shareCommunityMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public Integer getShareCommunitys(Long userId) {
        QueryWrapper<ShareCommunity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("create_by", userId);

        Integer count = shareCommunityMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public PageResult<CommunityResponse> getShareItems(ShareCommuntiyRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<ShareCommunity> shareCommunities = shareCommunityMapper.getShareItems(request);
        PageInfo<ShareCommunity> pageInfo = new PageInfo<>(shareCommunities);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<ShareCommunity>>() {
        }.build(), new TypeBuilder<PageResult<CommunityResponse>>() {}.build());
    }

    @Override
    public List<ShareCommunity> getHotShare() {
        LambdaQueryWrapper<ShareCommunity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(ShareCommunity::getViews, ShareCommunity::getCreateAt);
        lambdaQueryWrapper.last("limit 5");
        List<ShareCommunity>  shareCommunities = shareCommunityMapper.selectList(lambdaQueryWrapper);

        return shareCommunities;
    }

    @Override
    public void cancellikes(Long id) {
        LambdaUpdateWrapper<ShareCommunity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ShareCommunity::getId, id)
                .setSql(" views = views - 1");

        shareCommunityMapper.update(null, lambdaUpdateWrapper);
    }
}
