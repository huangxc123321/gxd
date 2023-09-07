package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.MyOrderResponse;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManDTO;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManRequiresRequest;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManRequiresResponse;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.TechEconomicManFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户中心: 我的接单")
@RestController
@Slf4j
@ResponseBody
public class MyOrderController extends BaseController {
    @Resource
    TechEconomicManFeignApi techEconomicManFeignApi;


    @ApiOperation("获取技术经纪人的需求列表")
    @PostMapping("/tech/broker/getEconomicManRequires")
    MyOrderResponse getEconomicManRequires(@RequestBody TechEconomicManRequiresRequest request){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        request.setCreateBy(userId);

        TechEconomicManDTO techEconomicMan = techEconomicManFeignApi.getTechEconomicManByMobile(this.getMobile());
        if(techEconomicMan != null){
            request.setEconomicId(techEconomicMan.getId());
        }else{
            throw new BizException(UserErrorCode.USER_IS_NOT_ECONOMICMAN);
        }

        MyOrderResponse  myOrderResponse = techEconomicManFeignApi.getEconomicManRequires(request);

        return myOrderResponse;
    }


}
