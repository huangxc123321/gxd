package com.gxa.jbgsw.business.service.impl;

import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.Collaborate;
import com.gxa.jbgsw.business.entity.Harvest;
import com.gxa.jbgsw.business.entity.Message;
import com.gxa.jbgsw.business.mapper.CollaborateMapper;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.protocol.enums.CollaborateTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.MessageOriginEnum;
import com.gxa.jbgsw.business.protocol.enums.MessageTypeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.CollaborateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.business.service.HarvestService;
import com.gxa.jbgsw.business.service.MessageService;
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
}
