package com.gxa.jbgsw.app.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.app.feignapi.BillboardLifecycleFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "需求跟进管理")
@RestController
@Slf4j
@ResponseBody
public class BillboardLifecycleController extends BaseController {
    @Resource
    BillboardLifecycleFeignApi billboardLifecycleFeignApi;

    @ApiOperation("需求跟进新增")
    @PostMapping("/billboard/lifecycle/add")
    void add(@RequestBody BillboardlifecycleDTO billboardlifecycleDTO){
        billboardlifecycleDTO.setCreateAt(new Date());
        billboardlifecycleDTO.setCreateBy(this.getUserId());
        billboardlifecycleDTO.setUserName(this.getUserNick());

        billboardLifecycleFeignApi.add(billboardlifecycleDTO);
    }

    @ApiOperation("需求跟进列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "需求派单ID", name = "pid", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/billboard/lifecycle/list")
    List<BillboardlifecycleResponse> list(@RequestParam("pid") Long pid){
        List<BillboardlifecycleResponse> billboardlifecycleResponses = billboardLifecycleFeignApi.list(pid);
        if(CollectionUtils.isNotEmpty(billboardlifecycleResponses)){
            billboardlifecycleResponses.stream().forEach(s->{
                String startStr = DateUtil.format(s.getCreateAt(), DatePattern.NORM_DATE_PATTERN);
                Date start = DateUtil.parse(startStr, DatePattern.NORM_DATE_PATTERN);
                long day = DateUtil.between(start, new Date(), DateUnit.DAY);
                StringBuffer sb = new StringBuffer();
                if(day == 0){
                    // 当天
                    sb.append("今天 ").append(DateUtil.format(s.getCreateAt(),DatePattern.NORM_TIME_PATTERN));
                }else{
                    sb.append(DateUtil.format(s.getCreateAt(), DatePattern.NORM_DATETIME_PATTERN));
                }
                s.setShowDate(sb.toString());
            });
        }

        return billboardlifecycleResponses;
    }

}
