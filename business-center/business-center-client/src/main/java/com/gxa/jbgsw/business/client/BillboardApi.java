package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author huangxc
 */
public interface BillboardApi {

    @PostMapping("/billboard/add")
    void add(@RequestBody BillboardDTO billboardDTO);

    @GetMapping("/billboard/addPv")
    void addPv(@RequestParam("id") Long id);

    @PostMapping(value = "/billboard/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/billboard/cancel/top")
    void cancelTop(@RequestParam("id") Long id);

    @PostMapping(value = "/billboard/batchIdsTop",consumes = MediaType.APPLICATION_JSON_VALUE)
    void batchIdsTop(@RequestBody Long[] ids);

    @PostMapping("/billboard/pageQuery")
    PageResult<BillboardResponse> pageQuery(@RequestBody BillboardRequest request);

    @GetMapping("/billboard/updateSeqNo")
    void updateSeqNo(@RequestParam("id") Long id, @RequestParam("seqNo") Integer seqNo);

    @GetMapping("/billboard/detail")
    DetailInfoDTO detail(@RequestParam("id") Long id);

    @PostMapping("/user/center/queryMyPublish/")
    MyPublishBillboardResponse queryMyPublish(@RequestBody MyPublishBillboardRequest request);

    @PostMapping("/user/center/updateMyBillboard")
    void updateMyBillboard(@RequestBody BillboardDTO billboardDTO);

    @PostMapping("/user/center/queryMyReceiveBillboard/")
    MyReceiveBillboardResponse queryMyReceiveBillboard(@RequestBody MyReceiveBillboardRequest request);

    @PostMapping(value = "/billboard/batchInsert",consumes = MediaType.APPLICATION_JSON_VALUE)
    void batchInsert(@RequestBody BillboardDTO[] batchList);

    @PostMapping(value = "/billboard/batchQueryByIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<BillboardDTO> batchQueryByIds(@RequestBody Long[] billboardIds);

    @PostMapping("/billboard/audit")
    void audit(@RequestBody BillboardAuditDTO billboardAuditDTO);

    @GetMapping("/billboard/getById")
    BillboardDTO getById(@RequestParam("id") Long id);

    @PostMapping(value = "/billboard/insertBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void insertBatchIds(@RequestBody Long[] ids);

    @GetMapping("/billboard/pipei")
    void pipei(@RequestParam("id") Long id);

    @GetMapping("/billboard/updateUnitName")
    void updateUnitName(@RequestParam("oldUnitName") String oldUnitName, @RequestParam("unitName") String unitName);
}
