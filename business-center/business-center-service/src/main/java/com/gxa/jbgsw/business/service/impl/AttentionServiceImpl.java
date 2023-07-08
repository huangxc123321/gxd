package com.gxa.jbgsw.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxa.jbgsw.business.entity.Attention;
import com.gxa.jbgsw.business.mapper.AttentionMapper;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionInfo;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionRequest;
import com.gxa.jbgsw.business.protocol.dto.MyAttentionResponse;
import com.gxa.jbgsw.business.protocol.enums.AttentionTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.service.AttentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 我的关注 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {
    @Resource
    AttentionMapper attentionMapper;

    @Override
    public MyAttentionResponse queryMyAttentions(MyAttentionRequest myAttentionRequest) {
        MyAttentionResponse response = new MyAttentionResponse();

        // 先统计另外类型的关注数量
        if(AttentionTypeEnum.GOV.getCode().equals(myAttentionRequest.getType())){
            Integer bizNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.BUZ.getCode());
            Integer talentNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.TALENT.getCode());

            response.setBusBuzNum(bizNum);
            response.setTalentNum(talentNum);
        }
        else if(AttentionTypeEnum.BUZ.getCode().equals(myAttentionRequest.getType())){
            Integer govNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.GOV.getCode());
            Integer talentNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.TALENT.getCode());

            response.setGovNum(govNum);
            response.setTalentNum(talentNum);
        } else if(AttentionTypeEnum.TALENT.getCode().equals(myAttentionRequest.getType())){
            Integer govNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.GOV.getCode());
            Integer buzNum = getAttentionNum(myAttentionRequest.getCreateBy(), AttentionTypeEnum.BUZ.getCode());

            response.setGovNum(govNum);
            response.setBusBuzNum(buzNum);
        }

        // 获取该类型下的分页数据
        PageHelper.startPage(myAttentionRequest.getPageNum(), myAttentionRequest.getPageSize());
        List<MyAttentionInfo> attentionInfos = attentionMapper.getMyAttentionInfos(myAttentionRequest);





        return null;
    }

    Integer getAttentionNum(Long userId, Integer type){

        return 0;
    }



}
