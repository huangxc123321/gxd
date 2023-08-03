package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.BillboardEconomicRelatedFeignApi;
import com.gxa.jbgsw.admin.feignapi.TechEconomicManFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.ConstantsUtils;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.catalina.authenticator.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "技术经纪人管理")
@RestController
@Slf4j
@ResponseBody
public class TechEconomicManController extends BaseController {
    @Resource
    TechEconomicManFeignApi techEconomicManFeignApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    MapperFacade mapperFacade;


    @ApiOperation(value = "批量删除技术经纪人", notes = "批量删除技术经纪人")
    @PostMapping("/tech/broker/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        techEconomicManFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增技术经纪人信息")
    @PostMapping("/tech/broker/add")
    void add(@RequestBody TechEconomicManDTO techEconomicManDTO) throws BizException {
        techEconomicManDTO.setCreateBy(this.getUserId());
        techEconomicManDTO.setCreateAt(new Date());

        techEconomicManFeignApi.add(techEconomicManDTO);

        /**
         * 分配一个账号
         * 先判断手机号是否注册，如果没有注册则注册
         */
        UserDTO user = userFeignApi.getUserByMobile(techEconomicManDTO.getMobile());
        if(user == null){
            UserDTO userDTO = mapperFacade.map(user, UserDTO.class);
            userDTO.setNick(techEconomicManDTO.getName());
            userDTO.setAvatar(techEconomicManDTO.getAvatar());
            // 设置默认密码: 123456
            userDTO.setPassword(ConstantsUtils.defalutMd5Password);

            userFeignApi.add(userDTO);
        }
    }

    @ApiOperation("更新技术经纪人信息")
    @PostMapping("/tech/broker/update")
    void update(@RequestBody TechEconomicManDTO techEconomicManDTO) throws BizException {
        techEconomicManDTO.setUpdateBy(this.getUserId());
        techEconomicManDTO.setUpdateAt(new Date());

        techEconomicManFeignApi.update(techEconomicManDTO);
    }

    @ApiOperation("获取技术经纪人列表")
    @PostMapping("/tech/broker/pageQuery")
    PageResult<TechEconomicManResponse> pageQuery(@RequestBody TechEconomicManRequest request){
        PageResult<TechEconomicManResponse> pageResult = techEconomicManFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "技术经纪人ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/tech/broker/detail")
    public TechEconomicManDTO detail(@RequestParam("id")Long id){
        TechEconomicManResponse techEconomicManResponse = techEconomicManFeignApi.getTechEconomicManById(id);
        TechEconomicManDTO techEconomicManDTO = mapperFacade.map(techEconomicManResponse, TechEconomicManDTO.class);

        //评价

        // 需求派单: 根据经纪人，找到分配的需求
         // billboardEconomicRelatedFeignApi.getRelatedByTechManId(id);


        return techEconomicManDTO;
    }


}
