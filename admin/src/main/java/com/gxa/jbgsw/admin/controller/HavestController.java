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
    public HavestDTO getHavestById(@RequestParam("id")Long id){
        HavestDTO havestDTO = havestFeignApi.getHavestById(id);
        // maturity_level
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeEnum.maturity_level.name(), String.valueOf(havestDTO.getMaturityLevel()));
        if(dictionaryDTO != null){
            havestDTO.setMaturityLevelName(dictionaryDTO.getDicValue());
        }

        // 技术领域
        StringBuffer sb = new StringBuffer();
        TechnicalFieldClassifyDTO tfc1 = technicalFieldClassifyFeignApi.getById(Long.valueOf(havestDTO.getTechDomain()));
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
        havestDTO.setTechDomainName(sb.toString().replace("所有分类,", ""));


        // 榜单推荐，根据成果ID获取推荐榜单
        List<BillboardHarvestRelatedResponse> relateBillboards = billboardHarvestRelatedFeignApi.getBillboardstByHarvestId(id);
        if(relateBillboards != null){
            havestDTO.setBillboardHarvestRecommends(relateBillboards);
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
                    DictionaryDTO dict = dictionaryFeignApi.getByCache(DictionaryTypeEnum.broker_type.name(), modes[i]);
                    if(dict != null && i != modes.length -1){
                        xx.append(dict.getDicValue()).append(",");
                    }else if(dict != null && i != modes.length -1){
                        xx.append(dict.getDicValue());
                    }
                }
                s.setModeName(xx.toString());
            });
        }
        havestDTO.setHavestCollaborates(havestCollaborates);

        return havestDTO;
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
