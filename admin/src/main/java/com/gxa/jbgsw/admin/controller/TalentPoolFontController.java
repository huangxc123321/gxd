package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.TalentPoolFeignApi;
import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "帅才库管理")
@RestController
@Slf4j
@ResponseBody
public class TalentPoolFontController extends BaseController {
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    BillboardTalentRelatedApi billboardTalentRelatedApi;


    @ApiOperation(value = "批量删除帅才", notes = "批量删除帅才")
    @PostMapping("/talent/pool/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        talentPoolFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增帅才信息")
    @PostMapping("/talent/pool/add")
    void add(@RequestBody TalentPoolDTO talentPoolDTO) throws BizException {
        talentPoolDTO.setCreateBy(this.getUserId());

        talentPoolFeignApi.add(talentPoolDTO);
    }

    @ApiOperation("编辑帅才信息")
    @PostMapping("/talent/pool/update")
    void update(@RequestBody TalentPoolDTO talentPoolDTO) throws BizException {
        if(talentPoolDTO == null || talentPoolDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }
        talentPoolDTO.setUpdateBy(this.getUserId());

        talentPoolFeignApi.update(talentPoolDTO);
    }


    @ApiOperation("获取帅才列表")
    @PostMapping("/talent/pool/pageQuery")
    PageResult<TalentPoolResponse> pageQuery(@RequestBody TalentPoolRequest request){
        PageResult<TalentPoolResponse> pageResult = talentPoolFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }


    @ApiOperation("查看帅才信息")
    @GetMapping("/talent/pool/detail")
    TalentPoolDTO detail(@RequestParam("id") Long id) throws BizException {
        TalentPoolDTO talentPoolDTO = talentPoolFeignApi.getTalentPoolById(id);

        // 成果推荐: 根据揭榜单位
        List<HarvestBillboardRelatedDTO> billboardRecommends = billboardTalentRelatedApi.getBillboardRecommendByTalentId(id);
        talentPoolDTO.setBillboardRecommends(billboardRecommends);

        // 合作发起
        List<HavestCollaborateDTO> vals = billboardTalentRelatedApi.getCollaborateByTalentId(id);
        talentPoolDTO.setCollaborates(vals);


        return null;
    }

    @ApiOperation("帅才审核")
    @PostMapping("/talent/pool/auditing")
    void auditing(@RequestBody TalentPoolAuditingDTO talentPoolAuditingDTO) throws BizException {
        talentPoolAuditingDTO.setAuditDate(new Date());
        talentPoolAuditingDTO.setAuditUserId(this.getUserId());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            talentPoolAuditingDTO.setAuditUserName(userResponse.getNick());
        }
        talentPoolFeignApi.updateStatus(talentPoolAuditingDTO);
    }


    private UserResponse getUser(){
        Long userId = this.getUserId();
        String userInfo = stringRedisTemplate.opsForValue().get(RedisKeys.USER_INFO+userId);
        if(StrUtil.isBlank(userInfo)){
            throw new BizException(UserErrorCode.LOGIN_CODE_ERROR);
        }
        UserResponse userResponse = JSONObject.parseObject(userInfo, UserResponse.class);
        return userResponse;
    }


}
