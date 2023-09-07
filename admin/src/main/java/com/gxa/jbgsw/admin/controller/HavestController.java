package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.CharUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.*;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.CollaborateStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
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

@Api(tags = "成果管理")
@RestController
@Slf4j
@ResponseBody
public class HavestController extends BaseController {

    @Resource
    HavestFeignApi havestFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    BillboardHarvestRelatedFeignApi billboardHarvestRelatedFeignApi;
    @Resource
    CollaborateFeignApi collaborateFeignApi;
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;

    @ApiOperation("新增成果信息")
    @PostMapping("/havest/add")
    void add(@RequestBody HavestDTO havestDTO) throws BizException {
        havestDTO.setCreateBy(this.getUserId());
        havestDTO.setCreateAt(new Date());
        // 现在holder存储所属机构
        havestDTO.setUnitName(havestDTO.getHolder());

        havestFeignApi.add(havestDTO);
    }

    @ApiOperation(value = "批量删除成果", notes = "批量删除成果")
    @PostMapping("/havest/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        havestFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("修改成果信息")
    @PostMapping("/havest/update")
    void update(@RequestBody HavestDTO havestDTO) throws BizException {
        // 现在holder存储所属机构
        havestDTO.setUnitName(havestDTO.getHolder());
        havestFeignApi.update(havestDTO);
    }

    @ApiOperation("获取成果列表")
    @PostMapping("/havest/pageQuery")
    PageResult<HarvestResponse> pageQuery(@RequestBody HarvestRequest request){
        PageResult<HarvestResponse> pageResult = havestFeignApi.pageQuery(request);
        List<HarvestResponse> responses = pageResult.getList();
        if(CollectionUtils.isNotEmpty(responses)){
            responses.stream().forEach(s->{
                DictionaryDTO dicMaturityLevel = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.maturity_level.name(), String.valueOf(s.getMaturityLevel()));
                if(dicMaturityLevel != null){
                    s.setMaturityLevelName(dicMaturityLevel.getDicValue());
                }
            });
        }

        return pageResult;
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "成果ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/havest/getHavestById")
    public HavestPO getHavestById(@RequestParam("id")Long id){
        HavestPO havestPO = havestFeignApi.getHavestById(id);
        // maturity_level
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.maturity_level.name(), String.valueOf(havestPO.getMaturityLevel()));
        if(dictionaryDTO != null){
            havestPO.setMaturityLevelName(dictionaryDTO.getDicValue());
        }

        // 技术领域
        if(havestPO.getTechDomain() != null){
            TechnicalFieldClassifyDTO tfc = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain());
            if(tfc != null){
                havestPO.setTechDomainName(tfc.getName());
            }
        }
        if(havestPO.getTechDomain1() != null){
            TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain1());
            if(tfc1 != null){
                havestPO.setTechDomain1Name(tfc1.getName());
            }
        }
        if(havestPO.getTechDomain2() != null){
            TechnicalFieldClassifyDTO tfc2 = technicalFieldClassifyFeignApi.getById(havestPO.getTechDomain2());
            if(tfc2 != null){
                havestPO.setTechDomain2Name(tfc2.getName());
            }
        }

        // 榜单推荐，根据成果ID获取推荐榜单
        List<BillboardHarvestRelatedResponse> relateBillboards = billboardHarvestRelatedFeignApi.getBillboardstByHarvestId(id);
        if(relateBillboards != null){
            havestPO.setBillboardHarvestRecommends(relateBillboards);
        }

        // 合作发起
        List<HavestCollaborateDTO> havestCollaborates = collaborateFeignApi.getHavestCollaborates(id);
        if(havestCollaborates != null){
            havestCollaborates.stream().forEach(s->{
                s.setStatusName(CollaborateStatusEnum.getNameByIndex(s.getStatus()));

                String mode = s.getMode();
                String[] modes = mode.split(",");
                StringBuffer xx = new StringBuffer();
                for(int i=0; i<modes.length; i++){
                    DictionaryDTO dict = dictionaryFeignApi.getByCache(DictionaryTypeEnum.collaborate_mode.name(), modes[i]);
                    if(dict != null && i != modes.length -1){
                        xx.append(dict.getDicValue()).append(",");
                    }else{
                        xx.append(dict.getDicValue());
                    }
                }
                s.setModeName(xx.toString());
            });
        }
        havestPO.setHavestCollaborates(havestCollaborates);

        return havestPO;
    }


    @ApiOperation("获取联系人")
    @GetMapping("/havest/getContacts")
    List<String> getContacts(@RequestParam("contacts") String contacts){
        return havestFeignApi.getContacts(contacts);
    }


    @ApiOperation("获取所属机构")
    @GetMapping("/havest/getHoloder")
    List<String> getHoloder(@RequestParam("holder") String holder){
        return havestFeignApi.getHoloder(holder);
    }


}
