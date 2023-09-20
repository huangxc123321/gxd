package com.gxa.jbgsw.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.admin.feignapi.TechEconomicManAppraiseFeignApi;
import com.gxa.jbgsw.admin.feignapi.TechEconomicManFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.enums.TechEconomicManLevelEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.ConstantsUtils;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(tags = "技术经纪人管理")
@RestController
@Slf4j
@ResponseBody
public class TechEconomicManController extends BaseController {
    @Resource
    TechEconomicManFeignApi techEconomicManFeignApi;
    @Resource
    TechEconomicManAppraiseFeignApi techEconomicManAppraiseFeignApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "获取专业标签", notes = "获取专业标签")
    @GetMapping("/tech/broker/getLabels")
    public List<String> getLabels(){
        Map<String, String> map = new HashMap<>();
        List<String> list = techEconomicManFeignApi.getLabels();
        if(CollectionUtils.isNotEmpty(list)){
            for(int i=0; i<list.size(); i++){
                String val = list.get(i);
                String[] vals = val.split(",");
                for(int n=0; n<vals.length; n++){
                    map.put(vals[n], vals[n]);
                }
            }

            List<String> result = new ArrayList(map.keySet());
            return result;
        }

        return null;
    }

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

        // 根据手机号码判断是否已经存在该经纪人
        TechEconomicManDTO existDto = techEconomicManFeignApi.getTechEconomicManByMobile(techEconomicManDTO.getMobile());
        if(existDto != null){
            throw new BizException(BusinessErrorCode.MOBILE_IS_EXIST);
        }

        techEconomicManFeignApi.add(techEconomicManDTO);

        // 经纪人匹配榜单
        String key = RedisKeys.ECONOMIC_RELATED_RECOMMEND_TASK + techEconomicManDTO.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(techEconomicManDTO.getId()), 1, TimeUnit.MINUTES);

        /**
         * 分配一个账号
         * 先判断手机号是否注册，如果没有注册则注册
         */
        UserResponse user = userFeignApi.getUserByMobile(techEconomicManDTO.getMobile());
        if(user == null){
            UserDTO userDTO = mapperFacade.map(techEconomicManDTO, UserDTO.class);
            userDTO.setNick(techEconomicManDTO.getName());
            userDTO.setAvatar(techEconomicManDTO.getAvatar());
            // 设置默认密码: 123456
            userDTO.setPassword(ConstantsUtils.defalutMd5Password);
            userDTO.setUnitNature(UserTypeEnum.PERSON.getCode());
            // 个人类型: 1 经纪人 2 帅才 0 其它
            userDTO.setType(1);

            userFeignApi.add(userDTO);
        }
    }

    @ApiOperation("更新技术经纪人信息")
    @PostMapping("/tech/broker/update")
    void update(@RequestBody TechEconomicManDTO techEconomicManDTO) throws BizException {
        techEconomicManDTO.setUpdateBy(this.getUserId());
        techEconomicManDTO.setUpdateAt(new Date());

        techEconomicManFeignApi.update(techEconomicManDTO);

        // 经纪人匹配榜单
        String key = RedisKeys.ECONOMIC_RELATED_RECOMMEND_TASK + techEconomicManDTO.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(techEconomicManDTO.getId()), 1, TimeUnit.MINUTES);
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
        techEconomicManDTO.setLevelName(TechEconomicManLevelEnum.getNameByIndex(techEconomicManDTO.getLevel()));

        DictionaryDTO t = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.broker_type.name(), String.valueOf(techEconomicManDTO.getType()));
        if(t != null){
            techEconomicManDTO.setTypeName(t.getDicValue());
        }

        //评价

        // 需求派单: 根据经纪人，找到分配的需求

       // billboardEconomicRelatedFeignApi.getRelatedByTechManId(id);

        // 写消息，需求消息


        return techEconomicManDTO;
    }

    @ApiOperation("获取技术经纪人的评价")
    @PostMapping("/tech/economic/appraise/getAppraise")
    PageResult<TechEconomicManAppraiseResponse> getAppraise(@RequestBody TechEconomicManAppraiseRequest request) {
        return techEconomicManAppraiseFeignApi.getAppraise(request);
    }

    @ApiOperation(value = "删除技术经纪人评价", notes = "删除技术经纪人评价")
    @GetMapping("/tech/economic/appraise/delete")
    public void delete(@RequestParam Long id){
        techEconomicManAppraiseFeignApi.delete(id);
    }

    @ApiOperation("获取技术经纪人的需求列表")
    @PostMapping("/tech/broker/queryEconomicManRequires")
    PageResult<TechEconomicManRequiresResponse> queryEconomicManRequires(@RequestBody TechEconomicManRequiresRequest request){
        return techEconomicManFeignApi.queryEconomicManRequires(request);
    }


    @ApiOperation(value = "删除技术经纪人上传的合作协议", notes = "删除技术经纪人上传的合作协议")
    @GetMapping("/tech/economic/deleteAgreements")
    public void deleteAgreements(@RequestParam Long id){
        techEconomicManFeignApi.deleteAgreements(id);
    }



}
