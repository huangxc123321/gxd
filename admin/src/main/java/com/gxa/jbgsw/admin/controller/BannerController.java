package com.gxa.jbgsw.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.BannerFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "广告位管理")
@RestController
@Slf4j
@ResponseBody
public class BannerController extends BaseController {
    @Resource
    BannerFeignApi bannerFeignApi;
    @Resource
    UserFeignApi userFeignApi;

    @ApiOperation("获取广告位列表")
    @PostMapping("/banner/pageQuery")
    PageResult<BannerResponse> pageQuery(@RequestBody BannerRequest request){
        PageResult<BannerResponse> pageResult = bannerFeignApi.pageQuery(request);
        List<BannerResponse> response = pageResult.getList();

        List<Long> ids = new ArrayList<>();
        List<Long> finalIds = ids;
        response.stream().forEach(s->{
            finalIds.add(s.getCreateBy());
        });

        // 去重
        ids = finalIds.stream().distinct().collect(Collectors.toList());
        Long[] userIds = new Long[ids.size()];
        ids.toArray(userIds);
        List<UserResponse> userResponses = userFeignApi.getUserByIds(userIds);
        response.forEach(s->{
            UserResponse u = userResponses.stream()
                    .filter(user -> s.getCreateBy().equals(user.getId()))
                    .findAny()
                    .orElse(null);
            if(u != null){
                s.setCreateName(u.getNick());
            }
        });

        return pageResult;
    }

    @ApiOperation(value = "修改排序", notes = "修改排序")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(value = "当前序列号，每上下一次增加或者减少1", name = "seqNo", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/banner/updateSeqNo")
    public void updateSeqNo(@RequestParam("id")Long id, @RequestParam("seqNo") Integer seqNo){
        bannerFeignApi.updateSeqNo(id, seqNo);
    }

    @ApiOperation("新增广告位信息")
    @PostMapping("/banner/add")
    void add(@RequestBody BannerDTO[] bannerDTO) throws BizException {
        Long userId = this.getUserId();
        if(bannerDTO.length > 0){
            for(int i=0; i<bannerDTO.length; i++){
                bannerDTO[i].setCreateAt(new Date());
                bannerDTO[i].setCreateBy(userId);
            }
        }
        bannerFeignApi.add(bannerDTO);
    }


    @ApiOperation("更新广告位信息")
    @PostMapping("/banner/update")
    void update(@RequestBody BannerDTO bannerDTO) throws BizException {
        bannerDTO.setUpdateAt(new Date());
        bannerDTO.setUpdateBy(this.getUserId());

        bannerFeignApi.update(bannerDTO);
    }



    @ApiOperation(value = "批量删除广告位", notes = "批量删除广告位")
    @PostMapping("/banner/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        bannerFeignApi.deleteBatchIds(ids);
    }


    @ApiOperation("修改广告位使用状态: 状态: 0 生效  1 失效")
    @GetMapping("/banner/update/status")
    void updateStatus(@RequestParam("id")Long id, @RequestParam("status")Integer status) throws BizException {
        bannerFeignApi.updateStatus(id, status);
    }


}
