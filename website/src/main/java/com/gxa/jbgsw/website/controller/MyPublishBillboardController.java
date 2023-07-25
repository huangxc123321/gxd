package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.business.client.TechEconomicManAppraiseApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.BillboardFeignApi;
import com.gxa.jbgsw.website.feignapi.BillboardGainFeignApi;
import com.gxa.jbgsw.website.feignapi.HavestFeignApi;
import com.gxa.jbgsw.website.feignapi.TalentPoolFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "用户中心：我的发布榜单")
@RestController
@Slf4j
@ResponseBody
public class MyPublishBillboardController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;
    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    TechEconomicManAppraiseApi techEconomicManAppraiseApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @ApiOperation("获取我发布的榜单列表")
    @PostMapping("/user/center/queryMyPublish")
    MyPublishBillboardResponse queryMyPublish(@RequestBody MyPublishBillboardRequest request){

        request.setUserId(this.getUserId());
        MyPublishBillboardResponse response = billboardFeignApi.queryMyPublish(request);

        return response;
    }

    @ApiOperation(value = "删除我发布的榜单", notes = "删除我发布的榜单")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/user/center/deleteMyBillboard")
    public void deleteMyBillboard(@RequestParam("id") Long id){
        Long[] ids = {id};
        billboardFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("我要发榜")
    @PostMapping("/user/center/addMyBillboard")
    void addMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        billboardDTO.setCreateBy(this.getUserId());
        billboardDTO.setCreateAt(new Date());
        // 设置默认的待揭榜
        billboardDTO.setStatus(0);

        // 判断是否政府发布政府榜单
        // TODO: 2023/7/7 0007 现在的流程暂时不用判断

        billboardFeignApi.add(billboardDTO);
    }

    @ApiOperation("编辑我发布的榜单信息")
    @PostMapping("/user/center/updateMyBillboard")
    void updateMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        billboardFeignApi.updateMyBillboard(billboardDTO);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/user/center/myBillboardDetail")
    public DetailInfoDTO myBillboardDetail(@RequestParam("id")Long id){
        DetailInfoDTO detailInfo = billboardFeignApi.detail(id);

        // 获取揭榜
        List<BillboardGainDTO> billboardGainResponses = billboardGainFeignApi.getBillboardGainByPid(id);
        detailInfo.setBillboardGains(billboardGainResponses);

        // 成果推荐: 根据揭榜单位

        // 帅才推荐： 根据技术领域，研究方向确定

        // 技术经纪人推荐: 根据专业标签来推荐

        return detailInfo;
    }

    @ApiOperation("我的技术经纪人评分")
    @PostMapping("/user/center/addAppraise")
    void addAppraise(@RequestBody TechEconomicManAppraiseDTO techEconomicManAppraiseDTO) throws BizException {
        techEconomicManAppraiseDTO.setCreateAt(new Date());
        techEconomicManAppraiseDTO.setCreateBy(this.getUserId());
        if(techEconomicManAppraiseDTO.isAnonymous()){
            // 匿名发表评价
            techEconomicManAppraiseDTO.setName(null);
        }else{
            UserResponse userResponse = getUser();
            if(userResponse != null){
                techEconomicManAppraiseDTO.setName(userResponse.getNick());
            }
        }

        techEconomicManAppraiseApi.add(techEconomicManAppraiseDTO);
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
