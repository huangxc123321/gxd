package com.gxa.jbgsw.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.BillboardFeignApi;
import com.gxa.jbgsw.admin.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.DetailInfoDTO;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Api(tags = "榜单管理")
@RestController
@Slf4j
@ResponseBody
public class BillboardController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/billboard/detail")
    public DetailInfoDTO detail(@RequestParam("id")Long id){
        DetailInfoDTO detailInfo = billboardFeignApi.detail(id);
        // 获取揭榜


        return detailInfo;
    }



    @ApiOperation("获取榜单列表")
    @PostMapping("/billboard/pageQuery")
    PageResult<BillboardResponse> pageQuery(@RequestBody BillboardRequest request){
        PageResult<BillboardResponse> pageResult = billboardFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "修改排序", notes = "修改排序")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(value = "当前序列号，每上下一次增加或者减少1", name = "seqNo", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/billboard/updateSeqNo")
    public void updateSeqNo(@RequestParam("id")Long id, @RequestParam("seqNo") Integer seqNo){
        billboardFeignApi.updateSeqNo(id, seqNo);
    }

    @ApiOperation(value = "批量置顶", notes = "批量置顶")
    @PostMapping("/billboard/batchIdsTop")
    public void batchIdsTop(@RequestBody Long[] ids){
        billboardFeignApi.batchIdsTop(ids);
    }

    @ApiOperation(value = "取消置顶", notes = "取消置顶")
    @PostMapping("/billboard/cancel/top")
    public void cancelTop(@RequestBody Long id){
        billboardFeignApi.cancelTop(id);
    }

    @ApiOperation(value = "批量删除榜单", notes = "批量删除榜单")
    @PostMapping("/billboard/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        billboardFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增榜单信息")
    @PostMapping("/billboard/add")
    void add(@RequestBody BillboardDTO billboardDTO) throws BizException {
        billboardDTO.setCreateBy(this.getUserId());
        // 设置默认的待揭榜
        billboardDTO.setStatus(0);

        // 判断是否政府发布政府榜单
        UserResponse userResponse = getUser();
        // 如果榜单是政府榜单，但用户又不是政府部门，那么抛出异常
        // TODO: 2023/6/29 0029 暂时不验证
        /*if(billboardDTO.getType().equals(Integer.valueOf(0)) && !userResponse.getLevel().equals(Integer.valueOf(0))){
            throw new BizException(BusinessErrorCode.GOV_BILLBOARD_PUBLISH_ERROR);
        }
        */
        
        billboardFeignApi.add(billboardDTO);
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
