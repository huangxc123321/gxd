package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.admin.feignapi.BillboardGainFeignApi;
import com.gxa.jbgsw.admin.feignapi.HavestFeignApi;
import com.gxa.jbgsw.admin.feignapi.LastBillboardSetFeignApi;
import com.gxa.jbgsw.admin.feignapi.TalentPoolFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @ApiOperation(value = "置顶", notes = "置顶")
    @PostMapping("/last/billboard/set/top")
    public void top(@RequestBody Long id){
        lastBillboardSetFeignApi.top(id);
    }

    @ApiOperation(value = "取消置顶", notes = "取消置顶")
    @PostMapping("/billboard/cancel/top")
    public void cancelTop(@RequestBody Long id){
        lastBillboardSetFeignApi.cancelTop(id);
    }

    @ApiOperation(value = "批量删除榜单", notes = "批量删除榜单")
    @PostMapping("/billboard/deleteBatchIds")
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
    @GetMapping("/billboard/detail")
    public DetailInfoDTO detail(@RequestParam("id")Long id){
        DetailInfoDTO detailInfo = lastBillboardSetFeignApi.detail(id);

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
