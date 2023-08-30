package com.gxa.jbgsw.business.controller;


import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.ShareCommunityApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.ShareCommunity;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.enums.ShareCommunityStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.ShareCommunityTypeEnum;
import com.gxa.jbgsw.business.service.ShareCommunityService;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "分享社区管理")
public class ShareCommunityController implements ShareCommunityApi {
    @Resource
    ShareCommunityService shareCommunityService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(ShareCommunityDTO shareCommunityDTO) {
        ShareCommunity shareCommunity = mapperFacade.map(shareCommunityDTO, ShareCommunity.class);

        shareCommunityService.save(shareCommunity);
    }

    @Override
    public void update(ShareCommunityDTO shareCommunityDTO) {
        ShareCommunity shareCommunity = shareCommunityService.getById(shareCommunityDTO.getId());
        BeanUtils.copyProperties(shareCommunityDTO, shareCommunity);

        shareCommunityService.updateById(shareCommunity);
    }

    @Override
    public void updateStatus(ShareCommunityAuditDTO shareCommunityAuditDTO) {
        shareCommunityService.updateStatus(shareCommunityAuditDTO);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        shareCommunityService.deleteBatchIds(ids);
    }

    @Override
    public ShareCommunityDetailDTO detail(Long id) {
        return shareCommunityService.detail(id);
    }

    @Override
    public PageResult<ShareCommunityResponse> pageQuery(ShareCommunityRequest request) {
        PageResult<ShareCommunityResponse> pages = new PageResult<>();

        PageResult<ShareCommunity> pageResult = shareCommunityService.pageQuery(request);
        List<ShareCommunity> shareCommunities = pageResult.getList();
        if(CollectionUtils.isNotEmpty(shareCommunities)){
            List<ShareCommunityResponse> responses = mapperFacade.mapAsList(shareCommunities, ShareCommunityResponse.class);
            // 转换数据
            responses.stream().forEach(s->{
                s.setTypeName(ShareCommunityTypeEnum.getNameByIndex(s.getType()));
                s.setStatusName(ShareCommunityStatusEnum.getNameByIndex(s.getStatus()));
            });

            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public PageResult<MyShareCommunityResponse> getMyShareCommunityPages(MyShareCommunityRequest request) {
        PageResult<MyShareCommunityResponse> pages = new PageResult<>();

        PageResult<ShareCommunity> pageResult = shareCommunityService.getMyShareCommunityPages(request);
        List<ShareCommunity> shareCommunities = pageResult.getList();
        if(CollectionUtils.isNotEmpty(shareCommunities)){
            List<MyShareCommunityResponse> responses = mapperFacade.mapAsList(shareCommunities, MyShareCommunityResponse.class);
            // 转换数据
            responses.stream().forEach(s->{
                s.setStatusName(ShareCommunityStatusEnum.getNameByIndex(s.getStatus()));
            });

            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public void addViews(Long id) {
        shareCommunityService.addViews(id);
    }

    @Override
    public void addlikes(Long id, Long userId) {
        shareCommunityService.addlikes(id, userId);
    }

    @Override
    public void addComments(Long id) {
        shareCommunityService.addComments(id);
    }

    @Override
    public Integer getShareCommunitys(Long userId) {
        return shareCommunityService.getShareCommunitys(userId);
    }

    @Override
    public PageResult<CommunityResponse> getShareItems(ShareCommuntiyRequest request) {
        return shareCommunityService.getShareItems(request);
    }

    @Override
    public List<CommunityResponse> getHotShare() {
         List<ShareCommunity> shareCommunities = shareCommunityService.getHotShare();
         if(CollectionUtils.isNotEmpty(shareCommunities)){
             return mapperFacade.mapAsList(shareCommunities, CommunityResponse.class);
         }

         return null;
    }

    @Override
    public void cancellikes(Long id, Long userId) {
        shareCommunityService.cancellikes(id, userId);
    }
}

