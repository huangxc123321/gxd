package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "最新榜单设置管理")
@RestController
@Slf4j
@ResponseBody
public class LastBillboardSetController extends BaseController {
    @Resource
    LastBillboardSetFeignApi lastBillboardSetFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;
    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "置顶", notes = "置顶")
    @GetMapping("/last/billboard/set/top")
    public void top(@RequestParam(value = "id") Long id){
        lastBillboardSetFeignApi.top(id);
    }

    @ApiOperation(value = "取消置顶", notes = "取消置顶")
    @GetMapping("/last/billboard/cancel/top")
    public void cancelTop(@RequestParam(value = "id") Long id){
        lastBillboardSetFeignApi.cancelTop(id);
    }

    @ApiOperation(value = "批量删除榜单", notes = "批量删除榜单")
    @PostMapping("/last/billboard/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        lastBillboardSetFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation(value = "获取最新榜单列表", notes = "获取最新榜单列表")
    @PostMapping("/last/billboard/pageQuery")
    PageResult<LastBillboardResponse> pageQuery(@RequestBody LastBillboardRequest request) {
        return lastBillboardSetFeignApi.pageQuery(request);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/last/billboard/detail")
    public DetailInfoDTO detail(@RequestParam("id")Long id){
        DetailInfoDTO detailInfo = lastBillboardSetFeignApi.detail(id);
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(detailInfo.getCategories()));
        if(dictionaryDTO != null){
            // 工信大类名称
            detailInfo.setCategoriesName(dictionaryDTO.getDicValue());
        }

        // 获取揭榜
        List<BillboardGainDTO> billboardGainResponses = billboardGainFeignApi.getBillboardGainByPid(id);
        if(CollectionUtils.isNotEmpty(billboardGainResponses)){
            billboardGainResponses.stream().forEach(s->{
                s.setAuditingStatusName(AuditingStatusEnum.getNameByIndex(s.getAuditingStatus()));
            });
        }
        detailInfo.setBillboardGains(billboardGainResponses);

        // 成果推荐: 根据揭榜单位

        // TODO: 2023/7/21 0021

        // 帅才推荐： 根据技术领域，研究方向确定


        // 技术经纪人推荐: 根据专业标签来推荐


        return detailInfo;
    }

    @ApiOperation(value = "查看揭榜方案评审详情", notes = "查看揭榜方案评审详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单审核ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/last/billboard/getBillboardGainById")
    public BillboardGainDTO getBillboardGainById(@RequestParam(value = "id") Long id){
        return billboardGainFeignApi.getBillboardGainById(id);
    }

    @ApiOperation("揭榜方案评审提交")
    @PostMapping("/last/billboard/update")
    public void update(@RequestBody BillboardGainAuditDTO billboardGainAuditDTO) throws BizException {
        billboardGainAuditDTO.setAuditingUserId(this.getUserId());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            billboardGainAuditDTO.setAuditingUserName(userResponse.getNick());
        }

        billboardGainFeignApi.update(billboardGainAuditDTO);
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
