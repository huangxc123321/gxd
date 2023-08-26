package com.gxa.jbgsw.admin.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.MessageLogInfo;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.user.protocol.dto.UserDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.enums.UserTypeEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "榜单管理")
@RestController
@Slf4j
@ResponseBody
public class BillboardFontController extends BaseController {
    @Resource
    BillboardFeignApi billboardFeignApi;
    @Resource
    BillboardGainFeignApi billboardGainFeignApi;
    @Resource
    BillboardHarvestRelatedFeignApi billboardHarvestRelatedFeignApi;
    @Resource
    BillboardTalentRelatedFeignApi billboardTalentRelatedFeignApi;
    @Resource
    BillboardEconomicRelatedFeignApi billboardEconomicRelatedFeignApi;
    @Resource
    CompanyFeignApi companyFeignApi;
    @Resource
    MessageFeignApi messageFeignApi;
    @Resource
    TechEconomicManFeignApi techEconomicManFeignApi;
    @Resource
    UserFeignApi userFeignApi;
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
        List<BillboardGainDTO> billboardGainResponses = billboardGainFeignApi.getBillboardGainByPid(id);
        if(CollectionUtils.isNotEmpty(billboardGainResponses)){
            billboardGainResponses.stream().forEach(s->{
                s.setAuditingStatusName(AuditingStatusEnum.getNameByIndex(s.getAuditingStatus()));
            });
            List<Long> ids = new ArrayList<>();
            List<Long> finalIds = ids;
            billboardGainResponses.stream().forEach(s->{
                if(s.getCreateBy() != null){
                    finalIds.add(s.getCreateBy());
                }
            });

            // 去重
            ids = finalIds.stream().distinct().collect(Collectors.toList());
            Long[] createByIds = new Long[ids.size()];
            ids .toArray(createByIds);
            List<UserResponse> createByResponses = userFeignApi.getUserByIds(createByIds);
            billboardGainResponses.stream().forEach(s->{
                if(s.getCreateBy() != null){
                    UserResponse u = createByResponses.stream()
                            .filter(user -> s.getCreateBy().equals(user.getId()))
                            .findAny()
                            .orElse(null);
                    if(u != null){
                        s.setCreateByName(u.getNick());
                    }
                }
            });
        }
        detailInfo.setBillboardGains(billboardGainResponses);

        // 成果推荐: 根据揭榜单位
        List<BillboardHarvestRelatedResponse> havests = billboardHarvestRelatedFeignApi.getHarvestRecommend(id);
        detailInfo.setHarvestRecommends(havests);

        // 帅才推荐： 根据技术领域，研究方向确定
        List<BillboardTalentRelatedResponse> talentRecommends = billboardTalentRelatedFeignApi.getTalentRecommend(id);
        detailInfo.setTalentRecommends(talentRecommends);

        // 技术经纪人推荐: 根据专业标签来推荐
        List<BillboardEconomicRelatedResponse> techBrokerRecommends = billboardEconomicRelatedFeignApi.getEconomicRecommend(id);
        detailInfo.setTechBrokerRecommends(techBrokerRecommends);

        return detailInfo;
    }

    @ApiOperation("获取榜单列表")
    @PostMapping("/billboard/pageQuery")
    PageResult<BillboardResponse> pageQuery(@RequestBody BillboardRequest request){
        PageResult<BillboardResponse> pageResult = billboardFeignApi.pageQuery(request);

        List<BillboardResponse> response = pageResult.getList();
        List<Long> ids = new ArrayList<>();
        List<Long> finalIds = ids;

        // 创建者
        List<Long> idCs = new ArrayList<>();
        List<Long> finalIdCs = idCs;
        if(response != null){
            response.stream().forEach(s->{
                if(s.getCreateBy() != null){
                    finalIdCs.add(s.getCreateBy());
                }
                if(s.getAuditUserId() != null){
                    finalIds.add(s.getAuditUserId());
                }
            });

            // 去重
            idCs = finalIdCs.stream().distinct().collect(Collectors.toList());
            Long[] createByIds = new Long[idCs.size()];
            idCs .toArray(createByIds);
            List<UserResponse> createByResponses = userFeignApi.getUserByIds(createByIds);
            response.forEach(s->{
                if(s.getCreateBy() != null){
                    UserResponse u = createByResponses.stream()
                            .filter(user -> s.getCreateBy().equals(user.getId()))
                            .findAny()
                            .orElse(null);
                    if(u != null){
                        s.setCreateByName(u.getNick());
                    }
                }
            });


            // 去重
            ids = finalIds.stream().distinct().collect(Collectors.toList());
            Long[] userIds = new Long[ids.size()];
            ids.toArray(userIds);
            if(CollectionUtils.isNotEmpty(ids)){
                List<UserResponse> userResponses = userFeignApi.getUserByIds(userIds);
                if(CollectionUtils.isNotEmpty(userResponses)){
                    response.forEach(s->{
                        if(s.getAuditUserId() != null){
                            UserResponse u = userResponses.stream()
                                    .filter(user -> s.getAuditUserId().equals(user.getId()))
                                    .findAny()
                                    .orElse(null);
                            if(u != null){
                                s.setAuditUserName(u.getNick());
                            }
                        }
                    });
                }
            }
        }

        return pageResult;
    }

    @ApiOperation(value = "修改排序", notes = "修改排序")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(value = "在当前的序号基础上是增加或者减少1", name = "seqNo", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/billboard/updateSeqNo")
    public void updateSeqNo(@RequestParam("id")Long id, @RequestParam("seqNo") Integer seqNo){



        billboardFeignApi.updateSeqNo(id, seqNo);
    }

    @ApiOperation(value = "榜单审核", notes = "榜单审核")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(value = "审核状态：  1 审核通过  2 审核不通过", name = "id", dataType = "Long", paramType = "query"),
    })
    @PostMapping("/billboard/audit")
    public void audit(@ RequestBody BillboardAuditDTO billboardAuditDTO){
        billboardAuditDTO.setAuditCreateAt(new Date());
        billboardAuditDTO.setAuditUserId(this.getUserId());

        billboardFeignApi.audit(billboardAuditDTO);

        BillboardDTO billboardDTO = billboardFeignApi.getById(billboardAuditDTO.getId());
        // 写系统消息
        MessageDTO messageDTO = new MessageDTO();
        // 时间
        messageDTO.setCreateAt(new Date());
        // 内容
        String content = String.format(MessageLogInfo.billboard_auth,
                       billboardDTO.getTitle(), AuditingStatusEnum.getNameByIndex(billboardAuditDTO.getAuditStatus()));
        messageDTO.setContent(content);
        // 榜单发布人
        messageDTO.setUserId(billboardDTO.getCreateBy());
        messageDTO.setTitle(content);
        // 系统消息
        messageDTO.setType(0);
        // 榜单ID
        messageDTO.setPid(billboardDTO.getId());
        messageFeignApi.add(messageDTO);
    }

    @ApiOperation(value = "批量置顶", notes = "批量置顶")
    @PostMapping("/billboard/batchIdsTop")
    public void batchIdsTop(@RequestBody Long[] ids){
        billboardFeignApi.batchIdsTop(ids);
    }

    @ApiOperation(value = "取消置顶", notes = "取消置顶")
    @GetMapping("/billboard/cancel/top")
    public void cancelTop(@RequestParam("id") Long id){
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
        if(StrUtil.isBlank(billboardDTO.getUnitName())){
            billboardDTO.setUnitName(this.getUnitName());
        }

        // 判断是否政府发布政府榜单
        UserResponse userResponse = getUser();
        // 如果榜单是政府榜单，但用户又不是政府部门，那么抛出异常
        if(billboardDTO.getType().equals(BillboardTypeEnum.GOV_BILLBOARD.getCode()) && !userResponse.getUnitNature().equals(UserTypeEnum.GOV.getCode())){
            throw new BizException(BusinessErrorCode.GOV_BILLBOARD_PUBLISH_ERROR);
        }else if(billboardDTO.getType().equals(BillboardTypeEnum.BUS_BILLBOARD.getCode())){
            boolean flag = false;
            if(userResponse.getUnitNature().equals(UserTypeEnum.BUZ.getCode())
                    || userResponse.getUnitNature().equals(UserTypeEnum.TEAM.getCode())
                    || userResponse.getUnitNature().equals(UserTypeEnum.EDU.getCode()) ){
                flag = true;
            }
            if(!flag){
                throw new BizException(BusinessErrorCode.GOV_BILLBOARD_PUBLISH_ERROR);
            }
        }

        if(userResponse != null){
            billboardDTO.setUnitName(userResponse.getUnitName());
        }

        billboardFeignApi.add(billboardDTO);
    }


    @ApiOperation("编辑我发布的榜单信息")
    @PostMapping("/user/center/updateMyBillboard")
    void updateMyBillboard(@RequestBody BillboardDTO billboardDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        if(billboardDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }
        billboardFeignApi.updateMyBillboard(billboardDTO);
    }

    @ApiOperation("成果手工推荐")
    @PostMapping("/billboard/harvest/related/audit")
    void auditHarvest(@RequestBody BillboardRelatedAuditDTO billboardHarvestAuditDTO){
        billboardHarvestAuditDTO.setUserId(this.getUserId());
        billboardHarvestAuditDTO.setUserName(this.getUserNick());
        billboardHarvestRelatedFeignApi.audit(billboardHarvestAuditDTO);
    }

    @ApiOperation("帅才手工推荐")
    @PostMapping("/billboard/talent/related/audit")
    void auditTalent(@RequestBody BillboardRelatedAuditDTO billboardHarvestAuditDTO){
        billboardHarvestAuditDTO.setUserId(this.getUserId());
        billboardHarvestAuditDTO.setUserName(this.getUserNick());
        billboardTalentRelatedFeignApi.audit(billboardHarvestAuditDTO);
    }

    @ApiOperation("手工派单")
    @PostMapping("/billboard/economic/related/auditEconomic")
    void auditEconomic(@RequestBody BillboardRelatedAuditDTO billboardHarvestAuditDTO){
        billboardHarvestAuditDTO.setUserId(this.getUserId());
        billboardHarvestAuditDTO.setUserName(this.getUserNick());
        billboardEconomicRelatedFeignApi.audit(billboardHarvestAuditDTO);

        // 写消息（派单： 系统在XXX时间给您派了一单，请立即查阅！）
        BillboardEconomicRelatedDTO billboardEconomicRelatedDTO = billboardEconomicRelatedFeignApi.getById(billboardHarvestAuditDTO.getId());
        if(billboardEconomicRelatedDTO != null){
            // 写系统消息
            MessageDTO messageDTO = new MessageDTO();
            // 时间
            messageDTO.setCreateAt(new Date());
            String time = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
            // 内容
            String content = String.format(MessageLogInfo.billboard_pd, time);
            messageDTO.setContent(content);
            // 派单接收人
            TechEconomicManResponse t = techEconomicManFeignApi.getTechEconomicManById(billboardEconomicRelatedDTO.getEconomicId());
            if(t != null){
                UserDTO userDTO = userFeignApi.getUserByMobile(t.getMobile());
                messageDTO.setUserId(userDTO.getId());
            }
            messageDTO.setTitle(content);
            // 派单： 类型为： 需求单
            messageDTO.setType(2);
            // 派单ID
            messageDTO.setPid(billboardEconomicRelatedDTO.getId());
            messageFeignApi.add(messageDTO);
        }
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
