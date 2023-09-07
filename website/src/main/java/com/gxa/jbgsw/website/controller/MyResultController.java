package com.gxa.jbgsw.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.CollaborateStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.CollectionStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.CollectionTypeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "用户中心: 我的成果")
@RestController
@Slf4j
@ResponseBody
public class MyResultController extends BaseController {
    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    CollectionFeignApi collectionFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    BillboardHarvestRelatedFeignApi billboardHarvestRelatedFeignApi;
    @Resource
    CollaborateFeignApi collaborateFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;

    @ApiOperation("获取我的成果列表")
    @PostMapping("/havest/pageQuery")
    PageResult<HarvestResponse> pageQuery(@RequestBody MyHarvestRequest myHarvestRequest){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        HarvestRequest harvestRequest = new HarvestRequest();
        harvestRequest.setCreateBy(userId);
        harvestRequest.setPageNum(myHarvestRequest.getPageNum());
        harvestRequest.setPageSize(myHarvestRequest.getPageSize());
        PageResult<HarvestResponse> pageResult = havestFeignApi.pageMyHarvestQuery(harvestRequest);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/havest/getHavestById")
    public HavestPO getHavestById(@RequestParam("id")Long id){
        HavestPO havestPO = havestFeignApi.getHavestById(id);
        // 判断是否收藏
        Long userId = this.getUserId();
        if(userId != null){
            CollectionDTO collectionDTO = null;
            // 收藏： 成果
            Integer collectionType = CollectionTypeEnum.HAVEST.getCode();

            collectionDTO = collectionFeignApi.getCollection(id, userId, collectionType);
            if(collectionDTO != null){
                havestPO.setCollectionStatus(CollectionStatusEnum.COLLECTION.getCode());
            }
        }

        // maturity_level
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.maturity_level.name(), String.valueOf(havestPO.getMaturityLevel()));
        if(dictionaryDTO != null){
            havestPO.setMaturityLevelName(dictionaryDTO.getDicValue());
        }

        // 榜单推荐，根据成果ID获取推荐榜单
        List<BillboardHarvestRelatedResponse> relateBillboards = billboardHarvestRelatedFeignApi.getBillboardstByHarvestId(id);
        if(relateBillboards != null){
            relateBillboards.stream().forEach(s->{
                DictionaryDTO dic = dictionaryFeignApi.getByCache(DictionaryTypeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dic != null){
                    s.setCategoriesName(dic.getDicValue());
                }
            });

            havestPO.setBillboardHarvestRecommends(relateBillboards);
        }

        // 合作发起
        List<HavestCollaborateDTO> havestCollaborates = collaborateFeignApi.getHavestCollaborates(id);
        if(havestCollaborates != null){
            havestCollaborates.stream().forEach(s->{
                s.setStatusName(CollaborateStatusEnum.getNameByIndex(s.getStatus() == null ? 0 : s.getStatus()));

                String mode = s.getMode();
                String[] modes = mode.split(",");
                StringBuffer sb = new StringBuffer();
                for(int i=0; i<modes.length; i++){
                    DictionaryDTO dict = dictionaryFeignApi.getByCache(DictionaryTypeEnum.broker_type.name(), modes[i]);
                    if(dict != null && i != modes.length -1){
                        sb.append(dict.getDicValue()).append(",");
                    }else if(dict != null && i != modes.length -1){
                        sb.append(dict.getDicValue());
                    }
                }
                s.setModeName(sb.toString());
            });
        }
        havestPO.setHavestCollaborates(havestCollaborates);

        return havestPO;
    }

    @ApiOperation("修改成果信息")
    @PostMapping("/havest/update")
    void update(@RequestBody HavestDTO havestDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        
        havestFeignApi.update(havestDTO);
    }

    @ApiOperation(value = "删除成果", notes = "删除成果")
    @PostMapping("/havest/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        havestFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("发布成果")
    @PostMapping("/havest/add")
    void add(@RequestBody HavestDTO havestDTO) throws BizException {
        havestDTO.setCreateBy(this.getUserId());
        havestDTO.setCreateAt(new Date());

        havestFeignApi.add(havestDTO);
    }


    @ApiOperation("成果推荐(用在右边的推荐)")
    @GetMapping("/havest/getRecommendHavest")
    List<RecommendHavestResponse> getRecommendHavest(){
        // 先时间，阅览数排序， 取3条数据
        List<RecommendHavestResponse> responses = havestFeignApi.getRecommendHavest();
        if(responses != null && responses.size() > 0){
            responses.stream().forEach(s->{
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

        return responses;
    }



}
