package com.gxa.jbgsw.business.service.impl;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.entity.*;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.mapper.CollaborateMapper;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateHarvestResponse;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateRequest;
import com.gxa.jbgsw.business.protocol.dto.MyCollaborateTalentResponse;
import com.gxa.jbgsw.business.protocol.enums.*;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.CollaborateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.MessageService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 我的合作 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class CollaborateServiceImpl extends ServiceImpl<CollaborateMapper, Collaborate> implements CollaborateService {
    @Resource
    CollaborateMapper collaborateMapper;
    @Resource
    MessageService messageService;
    @Resource
    HarvestService harvestService;
    @Resource
    BillboardService billboardService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<HavestCollaborateDTO> getHavestCollaborates(Long havestId) {
        return collaborateMapper.getHavestCollaborates(havestId);
    }

    @Override
    public void saveCollaborate(Collaborate collaborate) {
        collaborateMapper.insert(collaborate);

        // 写系统消息
        Message message = new Message();
        message.setUserId(collaborate.getLaunchUserId());
        message.setOrigin(MessageOriginEnum.COLLABORATE.getCode());
        if(collaborate.getPid() != null){
            // 成果
            StringBuffer sb = new StringBuffer();
            if(CollaborateTypeEnum.GAIN.getCode().equals(collaborate.getType())){
                Harvest harvest = harvestService.getById(collaborate.getPid());
                if(harvest != null){
                    sb.append(harvest.getName());
                }
            }else{
                Billboard billboard = billboardService.getById(collaborate.getPid());
                if(billboard != null){
                    sb.append(billboard.getTitle());
                }
            }
            sb.append("发起合作通知");

            message.setTitle(sb.toString());
            message.setPid(collaborate.getPid());
        }else{
            message.setTitle("合作通知");
        }
        message.setType(MessageTypeEnum.SYS.getCode());
        message.setCreateAt(new Date());
        message.setReadFlag(0);

        messageService.save(message);

    }

    @Override
    public void delete(Long id) {
        collaborateMapper.deleteById(id);
    }

    @Override
    public PageResult getCollaborateHarvest(MyCollaborateRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<MyCollaborateHarvestResponse> responses = collaborateMapper.getCollaborateHarvest(request);
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                s.setStatusName(CollaborateStatusEnum.getNameByIndex(s.getStatus()));
                String[] strs = s.getMode().split(",");
                StringBuffer sb = new StringBuffer();
                for(int i=0; i<strs.length; i++){
                    DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.collaborate_mode.name(), strs[i]);
                    if(i == strs.length -1){
                        sb.append(dic.getDicValue());
                    }else{
                        sb.append(dic.getDicValue()).append(CharUtil.COMMA);
                    }
                }
                s.setModeName(sb.toString());

            });
        }
        PageInfo<MyCollaborateHarvestResponse> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<MyCollaborateHarvestResponse>>() {
        }.build(), new TypeBuilder<PageResult<MyCollaborateHarvestResponse>>() {}.build());
    }

    @Override
    public PageResult getCollaborateTalent(MyCollaborateRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<MyCollaborateTalentResponse> responses = collaborateMapper.getCollaborateTalent(request);
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                s.setStatusName(CollaborateStatusEnum.getNameByIndex(s.getStatus()));
                String[] strs = s.getMode().split(",");
                StringBuffer sb = new StringBuffer();
                for(int i=0; i<strs.length; i++){
                    DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.collaborate_mode.name(), strs[i]);
                    if(i == strs.length -1){
                        sb.append(dic.getDicValue());
                    }else{
                        sb.append(dic.getDicValue()).append(CharUtil.COMMA);
                    }
                }
                s.setModeName(sb.toString());
            });
        }
        PageInfo<MyCollaborateTalentResponse> pageInfo = new PageInfo<>(responses);

        //类型转换
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<MyCollaborateTalentResponse>>() {
        }.build(), new TypeBuilder<PageResult<MyCollaborateTalentResponse>>() {}.build());
    }

    @Override
    public Collaborate getCollaborateInfo(Long userId, Long id) {
        LambdaQueryWrapper<Collaborate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collaborate::getLaunchUserId, userId);
        lambdaQueryWrapper.and(wrapper-> wrapper.eq(Collaborate::getLaunchUserId, userId).or().eq(Collaborate::getHarvestUserId, userId))
                .eq(Collaborate::getPid, id);

        List<Collaborate> collaborates = collaborateMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtils.isNotEmpty(collaborates)){
            return collaborates.get(0);
        }
        return null;
    }
}
