package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.BannerFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "广告位管理")
@RestController
@Slf4j
@ResponseBody
public class BannerController extends BaseController {
    @Resource
    BannerFeignApi bannerFeignApi;

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
        if(bannerDTO.length > 0){
            for(int i=0; i<bannerDTO.length; i++){
                bannerDTO[i].setCreateAt(new Date());
                bannerDTO[i].setCreateBy(this.getDepartmentId());
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


}
