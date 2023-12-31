package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.client.BillboardTalentRelatedApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.CollaborateStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.*;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserRequest;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "帅才库管理")
@RestController
@Slf4j
@ResponseBody
public class TalentPoolFontController extends BaseController {
    @Resource
    TalentPoolFeignApi talentPoolFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    BillboardTalentRelatedApi billboardTalentRelatedApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    MapperFacade mapperFacade;

    @ApiOperation(value = "批量删除帅才", notes = "批量删除帅才")
    @PostMapping("/talent/pool/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        talentPoolFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("新增帅才信息")
    @PostMapping("/talent/pool/add")
    void add(@RequestBody TalentPoolPO talentPoolPO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        talentPoolPO.setCreateBy(userId);
        talentPoolFeignApi.add(talentPoolPO);

        /**
         * 分配一个账号
         * 先判断手机号是否注册，如果没有注册则注册
         */
        if(StrUtil.isNotBlank(talentPoolPO.getMobie())){
            UserResponse user = userFeignApi.getUserByMobile(talentPoolPO.getMobie());
            if(user == null){
                UserDTO userDTO = mapperFacade.map(talentPoolPO, UserDTO.class);
                userDTO.setNick(talentPoolPO.getName());
                userDTO.setAvatar(talentPoolPO.getPhoto());
                // 设置默认密码: 123456
                userDTO.setPassword(ConstantsUtils.defalutMd5Password);
                userDTO.setMobile(talentPoolPO.getMobie());
                userDTO.setUnitNature(UserTypeEnum.PERSON.getCode());
                // 个人类型: 1 经纪人 2 帅才 0 其它
                userDTO.setType(2);  // 经纪人

                userFeignApi.add(userDTO);
            }
        }
    }

    @ApiOperation("编辑帅才信息")
    @PostMapping("/talent/pool/update")
    void update(@RequestBody TalentPoolUpdateDTO talentPoolUpdateDTO) throws BizException {
        if(talentPoolUpdateDTO == null || talentPoolUpdateDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }

        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        talentPoolUpdateDTO.setUpdateBy(userId);
        talentPoolUpdateDTO.setUpdateAt(new Date());

        talentPoolFeignApi.update(talentPoolUpdateDTO);
    }


    @ApiOperation("获取帅才列表")
    @PostMapping("/talent/pool/pageQuery")
    PageResult<TalentPoolResponse> pageQuery(@RequestBody TalentPoolRequest request){
        PageResult<TalentPoolResponse> pageResult = talentPoolFeignApi.pageQuery(request);
        List<TalentPoolResponse> responses = pageResult.getList();
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                s.setStatusName(AuditingStatusEnum.getNameByIndex(s.getStatus()));

                // 职称
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }

                // 学历显示名称
                DictionaryDTO edu = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.highest_edu.name(),  s.getHighestEdu());
                if(edu != null){
                    s.setHighestEduName(edu.getDicValue());
                }

                // 技术领域
                if(s.getTechDomain() != null){
                    TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(s.getTechDomain());
                    if(tfc != null){
                      s.setTechDomainName(tfc.getName());
                    }
                }
                if(s.getTechDomain1() != null){
                    TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(s.getTechDomain1());
                    if(tfc1 != null){
                        s.setTechDomain1Name(tfc1.getName());
                    }
                }
                if(s.getTechDomain2() != null){
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(s.getTechDomain2());
                    if(tfc2 != null){
                        s.setTechDomain2Name(tfc2.getName());
                    }
                }
            });
        }

        return pageResult;
    }


    @ApiOperation("查看帅才信息")
    @GetMapping("/talent/pool/detail")
    TalentPoolDTO detail(@RequestParam("id") Long id) throws BizException {
        TalentPoolDTO talentPoolDTO = talentPoolFeignApi.getTalentPoolById(id);

        // 成果推荐: 根据揭榜单位
        List<HarvestBillboardRelatedDTO> billboardRecommends = billboardTalentRelatedApi.getBillboardRecommendByTalentId(id);
        if(billboardRecommends != null){
            billboardRecommends.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.categories.name(),String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
            });
        }
        talentPoolDTO.setBillboardRecommends(billboardRecommends);

        // 合作发起
        List<HavestCollaborateDTO> vals = billboardTalentRelatedApi.getCollaborateByTalentId(id);
        talentPoolDTO.setCollaborates(vals);
        if(CollectionUtils.isNotEmpty(vals)){
            vals.stream().forEach(s->{
                s.setStatusName(CollaborateStatusEnum.getNameByIndex(s.getStatus()));
                String[] strs = s.getMode().split(",");
                StringBuffer sb = new StringBuffer();
                for(int i=0; i<strs.length; i++){
                    DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.collaborate_mode.name(), strs[i]);
                    if(i == strs.length -1){
                        sb.append(dic.getDicValue());
                    }else{
                        sb.append(dic.getDicValue()).append(CharUtil.COMMA);
                    }
                }
                s.setModeName(sb.toString());
            });
        }

        return talentPoolDTO;
    }

    @ApiOperation("帅才审核")
    @PostMapping("/talent/pool/auditing")
    void auditing(@RequestBody TalentPoolAuditingDTO talentPoolAuditingDTO) throws BizException {
        talentPoolAuditingDTO.setAuditDate(new Date());
        talentPoolAuditingDTO.setAuditUserId(this.getUserId());
        UserResponse userResponse = getUser();
        if(userResponse != null){
            talentPoolAuditingDTO.setAuditUserName(userResponse.getNick());
        }
        talentPoolFeignApi.updateStatus(talentPoolAuditingDTO);

        // 写日志 帅才审核：
        MessageDTO messageDTO = new MessageDTO();
        // 时间
        messageDTO.setCreateAt(new Date());
        // 内容
        String content = String.format(MessageLogInfo.talent_auth,
                AuditingStatusEnum.getNameByIndex(talentPoolAuditingDTO.getStatus()));
        messageDTO.setContent(content);
        // 帅才ID-->user_id
        TalentPoolDTO talentPool = talentPoolFeignApi.getTalentPoolById(talentPoolAuditingDTO.getId());
        if(talentPool != null){
            UserResponse userDTO =  userFeignApi.getUserByMobile(talentPool.getMobie());
            if(userDTO != null){
                messageDTO.setUserId(userDTO.getId());
            }
        }
        messageDTO.setTitle(content);
        // 系统消息
        messageDTO.setType(0);
        messageFeignApi.add(messageDTO);

    }

    @ApiOperation("获取所在单位")
    @GetMapping("/talent/pool/getUnits")
    List<String> getUnits(@RequestParam("unitName") String unitName){
        return talentPoolFeignApi.getUnits(unitName);
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
