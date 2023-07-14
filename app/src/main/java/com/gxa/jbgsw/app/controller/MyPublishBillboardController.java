package com.gxa.jbgsw.app.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.app.feignapi.BillboardFeignApi;
import com.gxa.jbgsw.app.feignapi.BillboardGainFeignApi;
import com.gxa.jbgsw.app.feignapi.HavestFeignApi;
import com.gxa.jbgsw.app.feignapi.TalentPoolFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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


    @ApiOperation("获取我发布的榜单列表")
    @PostMapping("/user/center/queryMyPublish/")
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
        List<HavestDTO> havests = havestFeignApi.getHarvesByHolder(detailInfo.getUnitName());
        detailInfo.setHarvestRecommends(havests);

        // 帅才推荐： 根据技术领域，研究方向确定
        String techKeys = detailInfo.getTechKeys();
        if(StrUtil.isNotBlank(techKeys)){
            String[] keys = techKeys.split(String.valueOf(CharUtil.COLON));
            List<TalentPoolDTO> talentPools = talentPoolFeignApi.getTalentPoolByTech(keys[0]);
            detailInfo.setTalentRecommends(talentPools);
        }

        // 技术经纪人推荐: 根据专业标签来推荐
        if(StrUtil.isNotBlank(techKeys)){
            String[] keys = techKeys.split(String.valueOf(CharUtil.COLON));


        }
        return detailInfo;
    }



}
