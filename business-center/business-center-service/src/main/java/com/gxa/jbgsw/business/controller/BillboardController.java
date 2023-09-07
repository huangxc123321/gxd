package com.gxa.jbgsw.business.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.business.client.BillboardApi;
import com.gxa.jbgsw.business.entity.Billboard;
import com.gxa.jbgsw.business.entity.BillboardTemporary;
import com.gxa.jbgsw.business.feignapi.DictionaryFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.business.protocol.enums.AuditingStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardStatusEnum;
import com.gxa.jbgsw.business.protocol.enums.BillboardTypeEnum;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.service.BillboardService;
import com.gxa.jbgsw.business.service.BillboardTemporaryService;
import com.gxa.jbgsw.business.service.CompanyService;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author huangxc
 */
@RestController
@Slf4j
@Api(tags = "榜单管理")
public class BillboardController implements BillboardApi {
    @Resource
    BillboardService billboardService;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    CompanyService companyService;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    BillboardTemporaryService billboardTemporaryService;

    @Override
    public void add(BillboardDTO billboardDTO) {
        Billboard billboard = mapperFacade.map(billboardDTO, Billboard.class);
        if(billboardDTO.isCreateVideo()){
            billboard.setIsCreateVideo(1);
        }
        billboard.setCreateAt(new Date());

        // 组装keys
        StringBuffer sb = new StringBuffer();
        // 标题
        sb.append(billboardDTO.getTitle());
        sb.append(CharUtil.COMMA);
        // 技术关键字（直接输入）
        sb.append(billboardDTO.getTechKeys());
        sb.append(CharUtil.COMMA);
        // 工信大类
        DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(billboardDTO.getCategories()));
        if(dictionaryDTO != null){
            sb.append(dictionaryDTO.getDicValue());
        }
        sb.append(CharUtil.COMMA);
        sb.append(billboard.getProvinceName()).append(CharUtil.COMMA)
                .append(billboard.getCityName()).append(CharUtil.COMMA)
                .append(billboard.getAreaName());
        billboard.setQueryKeys(sb.toString());

        // 如果是企业榜，看发布人是否为空， 如果不为空看是否可以查询到企业，如果能够查到，就吧图片加入
        if(BillboardTypeEnum.BUS_BILLBOARD.getCode().equals(billboardDTO.getType())
                && StrUtil.isNotBlank(billboardDTO.getUnitName())){
            CompanyDTO companyDTO = companyService.getCompanyByName(billboardDTO.getUnitName());
            if(companyDTO != null){
                billboard.setUnitLogo(companyDTO.getLogo());
            }
        }

        billboardService.save(billboard);

        // 发布过后生成成果推荐，帅才推荐，经纪人推荐相关数据, 写定时任务
        String key = RedisKeys.BILLBOARD_RELATED_RECOMMEND_TASK + billboard.getId();
        // 过期时间
        stringRedisTemplate.opsForValue().set(key, String.valueOf(billboard.getId()), 1, TimeUnit.MINUTES);

    }

    @Override
    public void addPv(Long id) {
        billboardService.add(id);
    }

    @Override
    public void deleteBatchIds(Long[] ids) {
        billboardService.deleteBatchIds(ids);
    }

    @Override
    public void cancelTop(Long id) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.updateTop(id, 0);
    }

    @Override
    public void batchIdsTop(Long[] ids) {
        // 是否置顶： 0 不置顶 1 置顶
        billboardService.batchIdsTop(ids, 1);
    }

    @Override
    public PageResult<BillboardResponse> pageQuery(BillboardRequest request) {
        PageResult<BillboardResponse> pages = new PageResult<>();

        PageResult<Billboard> pageResult = billboardService.pageQuery(request);
        List<Billboard> billboards = pageResult.getList();
        if(CollectionUtils.isNotEmpty(billboards)){
            List<BillboardResponse> responses = mapperFacade.mapAsList(billboards, BillboardResponse.class);
            responses.forEach(s->{
                DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                if(dictionaryDTO != null){
                    // 工信大类名称
                    s.setCategoriesName(dictionaryDTO.getDicValue());
                }
                // 状态名称
                s.setStatusName(BillboardStatusEnum.getNameByIndex(s.getStatus()));
                if(Integer.valueOf(1).equals(s.getIsCreateVideo())){
                    s.setCreateVideo(true);
                }
            });

            pages.setList(responses);
            pages.setPageNum(pageResult.getPageNum());
            pages.setPages(pageResult.getPages());
            pages.setPageSize(pageResult.getPageSize());
            pages.setSize(pageResult.getSize());
            pages.setTotal(pageResult.getTotal());
        }

        return pages;
    }

    @Override
    public void updateSeqNo(Long id, Integer seqNo) {
        billboardService.updateSeqNo(id, seqNo);
    }

    @Override

    public DetailInfoDTO detail(Long id) {
        Billboard billboard = billboardService.getById(id);
        DetailInfoDTO detailInfoDTO = mapperFacade.map(billboard, DetailInfoDTO.class);
        if(detailInfoDTO != null && detailInfoDTO.getCategories() != null){
            // 行业名称
            DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(detailInfoDTO.getCategories()));
            if(dictionaryDTO != null){
                // 工信大类名称
                detailInfoDTO.setCategoriesName(dictionaryDTO.getDicValue());
            }
        }

        return detailInfoDTO;
    }

    @Override
    public MyPublishBillboardResponse queryMyPublish(MyPublishBillboardRequest request) {
        MyPublishBillboardResponse response = new MyPublishBillboardResponse();

        // 先统计另外类型的总数
        Integer trueType = BillboardTypeEnum.GOV_BILLBOARD.getCode();
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            trueType = BillboardTypeEnum.BUS_BILLBOARD.getCode();
        }
        int num = billboardService.getPublishNum(request.getUserId(), trueType);

        // 政府榜
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            // 获取我发布的政府榜列表
            PageResult<MyPublishBillboardInfo> pageResult = billboardService.queryMyPublish(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(num);
            response.setGovBillboardsNum(pageResult.getTotal());
        }else{
            // 获取我发布的企业榜列表
            PageResult<MyPublishBillboardInfo> pageResult = billboardService.queryMyPublish(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(pageResult.getTotal());
            response.setGovBillboardsNum(num);
        }

        return response;
    }

    @Override
    public void updateMyBillboard(BillboardDTO billboardDTO) {
        billboardService.updateMyBillboard(billboardDTO);
    }

    @Override
    public MyReceiveBillboardResponse queryMyReceiveBillboard(MyReceiveBillboardRequest request) {
        MyReceiveBillboardResponse response = new MyReceiveBillboardResponse();

        // 先统计另外类型的总数
        Integer trueType = BillboardTypeEnum.GOV_BILLBOARD.getCode();
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            trueType = BillboardTypeEnum.BUS_BILLBOARD.getCode();
        }
        long num = billboardService.getMyReceiveBillboard(request.getUserId(), trueType);

        // 政府榜
        if(BillboardTypeEnum.GOV_BILLBOARD.getCode().equals(request.getType())){
            // 获取我发布的政府榜列表
            PageResult<MyReceiveBillboardInfo> pageResult = billboardService.queryMyReceiveBillboard(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setGovBillboardsNum(pageResult.getTotal());
            response.setBusBillboardsNum(num);
        }else{
            // 获取我发布的企业榜列表
            PageResult<MyReceiveBillboardInfo> pageResult = billboardService.queryMyReceiveBillboard(request);

            response.setBillboards(pageResult.getList());
            response.setPageNum(pageResult.getPageNum());
            response.setPageSize(pageResult.getPageSize());
            response.setSize(pageResult.getSize());
            response.setTotal(pageResult.getTotal());
            response.setPages(pageResult.getPages());
            response.setBusBillboardsNum(pageResult.getTotal());
            response.setGovBillboardsNum(num);
        }

        return response;
    }

    @Override
    public void batchInsert(BillboardDTO[] batchList) {
        List<BillboardDTO> list = Arrays.stream(batchList).collect(Collectors.toList());
        billboardService.batchInsert(list);
    }

    @Override
    public List<BillboardDTO> batchQueryByIds(@RequestBody Long[] billboardIds) {
        List<Long> ids = Arrays.stream(billboardIds).collect(Collectors.toList());

        List<Billboard> billboards = billboardService.listByIds(ids);
        return mapperFacade.mapAsList(billboardIds, BillboardDTO.class);
    }

    @Override
    public void audit(BillboardAuditDTO billboardAuditDTO) {
        billboardService.updateAuditStatus(billboardAuditDTO);
    }

    @Override
    public BillboardDTO getById(Long id) {
        Billboard billboard = billboardService.getById(id);

        return mapperFacade.map(billboard, BillboardDTO.class);
    }

    @Override
    public void insertBatchIds(Long[] ids) {
        List<Long> list = Arrays.stream(ids).collect(Collectors.toList());
        List<BillboardTemporary>  billboardTemporaries = billboardTemporaryService.listByIds(list);

        List<Billboard> billboards = mapperFacade.mapAsList(billboardTemporaries,  Billboard.class);
        billboards.stream().forEach(s->{
            // ID 重新置为null
            s.setId(null);

            // 组装keys
            StringBuffer sb = new StringBuffer();
            // 标题
            sb.append(s.getTitle());
            sb.append(CharUtil.COMMA);
            // 技术关键字（直接输入）
            sb.append(s.getTechKeys());
            sb.append(CharUtil.COMMA);
            // 工信大类
            DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
            if(dictionaryDTO != null){
                sb.append(dictionaryDTO.getDicValue());
            }
            sb.append(CharUtil.COMMA);
            sb.append(s.getProvinceName()).append(CharUtil.COMMA)
                    .append(s.getCityName()).append(CharUtil.COMMA)
                    .append(s.getAreaName());
            s.setQueryKeys(sb.toString());
        });

        Integer status = 0;
        try {
            billboardService.saveBatch(billboards);
        }catch (Exception ex){
            // 导入失败
            status = 1;
        }

        Long createBy = billboardTemporaries.get(0).getCreateBy();
        billboardTemporaryService.updateStatusByCreateBy(createBy, 0);

        // 删除临时数据
        billboardTemporaryService.deleteByCreateByAndIds(createBy, ids);

    }
}

