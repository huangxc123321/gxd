package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.CharUtil;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.website.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.website.feignapi.IndexFeignApi;
import com.gxa.jbgsw.website.feignapi.TechnicalFieldClassifyFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "页面右边推荐管理")
@RestController
@Slf4j
@ResponseBody
public class RightRecommendController extends BaseController {
    @Resource
    IndexFeignApi indexFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;

    @Deprecated
    @ApiOperation("根据榜单ID获取相关成果、帅才推荐、榜单推荐信息， （榜单详情页使用）已经弃用")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByBillboardId")
    ApiResult<RelateDTO> getRelatedByBillboardId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByBillboardId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }


    @ApiOperation("根据榜单ID获取相关榜单（榜单详情页使用,拆成三个接口）,相关榜单显示5条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedBillboardByBillboardId")
    List<RelateBillboardDTO> getRelatedBillboardByBillboardId(@RequestParam(value = "id") Long id) {
        return indexFeignApi.getRelatedBillboardByBillboardId(id);
    }

    @ApiOperation("根据榜单ID获取相关帅才推荐（榜单详情页使用,拆成三个接口）,相关帅才推荐显示一条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedTalentByBillboardId")
    List<RelateTalentDTO> getRelatedTalentByBillboardId(@RequestParam(value = "id") Long id) {
        List<RelateTalentDTO> relateTalents = indexFeignApi.getRelatedTalentByBillboardId(id);
        if(CollectionUtils.isNotEmpty(relateTalents)){
            relateTalents.stream().forEach(s->{
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
        }

        return relateTalents;
    }


    @ApiOperation("根据榜单ID获取相关成果（榜单详情页使用,拆成三个接口）,相关成果显示三条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "榜单ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedHavestByBillboardId")
    List<RelateHavestDTO> getRelatedHavestByBillboardId(@RequestParam(value = "id") Long id) {
        return indexFeignApi.getRelatedHavestByBillboardId(id);
    }


    @ApiOperation("根据成果ID获取相关成果、帅才推荐、榜单推荐信息， （成果详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByHarvestId")
    ApiResult<RelateDTO>  getRelatedByHarvestId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByHarvestId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }

    @ApiOperation("根据帅才ID获取相关成果、帅才推荐、榜单推荐信息， （帅才详情页使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帅才ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedByTalentId")
    ApiResult<RelateDTO>  getRelatedByTalentId(@RequestParam(value = "id") Long id) {
        ApiResult<RelateDTO> apiResult = new ApiResult<>();
        RelateDTO relateDTO = indexFeignApi.getRelatedByTalentId(id);
        apiResult.setData(relateDTO);

        return apiResult;
    }

    @ApiOperation("根据搜索条件获取相近的帅才")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "搜索词", name = "searchFiled", dataType = "String", paramType = "query"),
    })
    @PostMapping("/index/getRelatedTalentByKeys")
    List<RelateTalentDTO> getRelatedTalentByKeys(@RequestBody SearchParamsDTO searchParams) {
        List<RelateTalentDTO> relateTalents = indexFeignApi.getRelatedTalentByKeys(searchParams);
        if(CollectionUtils.isNotEmpty(relateTalents)){
            relateTalents.stream().forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dictionaryDTO != null){
                    s.setProfessionalName(dictionaryDTO.getDicValue());
                }

            });
        }

        return relateTalents;
    }




    @ApiOperation("根据公司ID获取相关成果,相关成果显示三条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公司ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedHavestByCompanyId")
    List<RelateHavestDTO> getRelatedHavestByCompanyId(@RequestParam(value = "id") Long id) {
        List<RelateHavestDTO>  relateHavests = indexFeignApi.getRelatedHavestByCompanyId(id);
        if(CollectionUtils.isNotEmpty(relateHavests)){
            relateHavests.stream().forEach(s->{
                // 技术领域
                StringBuffer sb = new StringBuffer();
                TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(s.getTechDomain()));
                if(tfc1 != null){
                    sb.append(tfc1.getName());
                    sb.append(CharUtil.COMMA);
                    TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc1.getPid()));
                    if(tfc2 != null){
                        sb.append(tfc2.getName());
                        sb.append(CharUtil.COMMA);
                        TechnicalFieldClassifyDTO tfc3 = technicalFieldClassifyFeignApi.getById(Long.valueOf(tfc2.getPid()));
                        if(tfc3 != null){
                            sb.append(tfc3.getName());
                        }
                    }
                }
                s.setTechDomainName(sb.toString().replace("所有分类,", ""));
            });
        }

        return relateHavests;
    }


    @ApiOperation("根据公司ID获取相关帅才推荐,相关帅才推荐显示一条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公司ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedTalentByCompanyId")
    List<RelateTalentDTO> getRelatedTalentByCompanyId(@RequestParam(value = "id") Long id) {
        List<RelateTalentDTO> relateTalents = indexFeignApi.getRelatedTalentByCompanyId(id);
        if(CollectionUtils.isNotEmpty(relateTalents)){
            relateTalents.stream().forEach(s->{
                DictionaryDTO dicProfessional = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.professional.name(), String.valueOf(s.getProfessional()));
                if(dicProfessional != null){
                    s.setProfessionalName(dicProfessional.getDicValue());
                }
            });
        }

        return relateTalents;
    }



    @ApiOperation("根据公司ID获取相关榜单，相关榜单显示5条")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公司ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/index/getRelatedBillboardByCompanyId")
    List<RelateBillboardDTO> getRelatedBillboardByCompanyId(@RequestParam(value = "id") Long id) {
        return indexFeignApi.getRelatedBillboardByCompanyId(id);
    }

}
