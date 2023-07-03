package com.gxa.jbgsw.business.service.impl;

import com.gxa.jbgsw.business.entity.TalentPool;
import com.gxa.jbgsw.business.mapper.TalentPoolMapper;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.service.TalentPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.BindException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 帅才库 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class TalentPoolServiceImpl extends ServiceImpl<TalentPoolMapper, TalentPool> implements TalentPoolService {
    @Resource
    TalentPoolMapper talentPoolMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void deleteBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        talentPoolMapper.deleteBatchIds(list);
    }

    @Override
    public void add(TalentPoolDTO talentPoolDTO) {
        TalentPool talentPool = mapperFacade.map(talentPoolDTO, TalentPool.class);
        talentPool.setId(null);
        talentPool.setCreateAt(new Date());
        this.save(talentPool);
    }

    @Override
    public void updateTalentPool(TalentPoolDTO talentPoolDTO) throws BizException {
        TalentPool talentPool = talentPoolMapper.selectById(talentPoolDTO.getId());
        // talentPoolDTO有null就不需要替换talentPool
        BeanUtils.copyProperties(talentPoolDTO, talentPool, CopyPropertionIngoreNull.getNullPropertyNames(talentPool));

        talentPoolMapper.updateById(talentPool);
    }
}
