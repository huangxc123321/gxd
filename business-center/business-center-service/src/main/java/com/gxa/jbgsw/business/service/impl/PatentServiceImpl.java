package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxa.jbgsw.business.entity.Patent;
import com.gxa.jbgsw.business.mapper.PatentMapper;
import com.gxa.jbgsw.business.protocol.dto.PatentDTO;
import com.gxa.jbgsw.business.service.PatentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 专利 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class PatentServiceImpl extends ServiceImpl<PatentMapper, Patent> implements PatentService {
    @Resource
    PatentMapper patentMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public List<PatentDTO> getPatentByPid(Long id) {
        LambdaQueryWrapper<Patent> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patent::getPid, id);

        List<Patent> patents = patentMapper.selectList(lambdaQueryWrapper);
        if(patents != null && patents.size()>0){
            return mapperFacade.mapAsList(patents, PatentDTO.class);
        }

        return null;
    }
}
