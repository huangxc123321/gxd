package com.gxa.jbgsw.business.service.impl;

import com.gxa.jbgsw.business.entity.Collaborate;
import com.gxa.jbgsw.business.mapper.CollaborateMapper;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import com.gxa.jbgsw.business.service.CollaborateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<HavestCollaborateDTO> getHavestCollaborates(Long havestId) {
        return collaborateMapper.getHavestCollaborates(havestId);
    }
}
