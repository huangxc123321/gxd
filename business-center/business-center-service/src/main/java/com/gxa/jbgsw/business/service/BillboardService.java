package com.gxa.jbgsw.business.service;

import com.gxa.jbgsw.business.entity.Billboard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageRequest;
import com.gxa.jbgsw.common.utils.PageResult;

import java.util.List;

/**
 * <p>
 * 榜单信息 服务类
 * </p>
 *
 * @author huangxc
 * @since 2023-06-26
 */
public interface BillboardService extends IService<Billboard> {

    void deleteBatchIds(Long[] ids);

    void updateTop(Long id, int isTop);

    void batchIdsTop(Long[] ids, int isTop);

    PageResult<Billboard> pageQuery(BillboardRequest request);

    void updateSeqNo(Long id, Integer seqNo);

    PageResult<Billboard> LastBillboardSetData(LastBillboardRequest request);

    int getPublishNum(Long userId, Integer type);

    PageResult<MyPublishBillboardInfo> queryMyPublish(MyPublishBillboardRequest request);

    void updateMyBillboard(BillboardDTO billboardDTO);

    long getMyReceiveBillboard(Long userId, Integer trueType);

    PageResult<MyReceiveBillboardInfo> queryMyReceiveBillboard(MyReceiveBillboardRequest request);

    IndexResponse getIndex();

    PageResult<BillboardIndexDTO> queryGovBillborads(SearchGovRequest searchGovRequest);

    PageResult<BillboardIndexDTO> queryBizBillborads(SearchBizRequest searchGovRequest);

    List<RelateHavestDTO> getRelatedHavestByBillboardId(Long id, int i);

    List<Billboard> getRelateBillboardByCategories(Integer categories, Integer type);

    void batchInsert(List<BillboardDTO> batchList);

    void updateAuditStatus(BillboardAuditDTO billboardAuditDTO);

    List<BillboardResponse> searchNew(Integer num);

    List<RelateTalentDTO> getRelatedTalentByKeys(SearchParamsDTO searchParams);

    List<RelateBillboardDTO> getRelatedBillboardByBillboardId(Long id);

    List<BillboardResponse> getBillboardByUnitName(String name);

    List<Billboard> getRelatedBillboardByCompanyId(Long id);
}
