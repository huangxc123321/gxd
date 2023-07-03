package com.gxa.jbgsw.business.service.impl;

import com.gxa.jbgsw.business.entity.Patent;
import com.gxa.jbgsw.business.mapper.PatentMapper;
import com.gxa.jbgsw.business.service.PatentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
