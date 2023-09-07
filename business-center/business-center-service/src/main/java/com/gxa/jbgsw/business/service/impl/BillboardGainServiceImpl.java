package com.gxa.jbgsw.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gxa.jbgsw.business.entity.BillboardGain;
import com.gxa.jbgsw.business.mapper.BillboardGainMapper;
import com.gxa.jbgsw.business.service.BillboardGainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 接榜 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class BillboardGainServiceImpl extends ServiceImpl<BillboardGainMapper, BillboardGain> implements BillboardGainService {
    @Resource
    BillboardGainMapper billboardGainMapper;

    @Override
    public List<BillboardGain> getBillboardGainByPid(Long id) {
        LambdaQueryWrapper<BillboardGain> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardGain::getPid, id);
        lambdaQueryWrapper.orderByDesc(BillboardGain::getApplyAt);

        return billboardGainMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public boolean getIsGain(Long pid, Long userId) {
        LambdaQueryWrapper<BillboardGain> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BillboardGain::getPid, pid);
        lambdaQueryWrapper.eq(BillboardGain::getCreateBy, userId);
        List<BillboardGain> billboardGains = billboardGainMapper.selectList(lambdaQueryWrapper);

        if(billboardGains != null && billboardGains.size()>0){
            return true;
        }
        return false;
    }
}
