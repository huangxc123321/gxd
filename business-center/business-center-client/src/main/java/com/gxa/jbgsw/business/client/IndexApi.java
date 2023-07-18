package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/7/10 0010 9:58
 * @Version 2.0
 */
public interface IndexApi {
    @GetMapping("/index/getIndex")
    public IndexResponse getIndex();

    @PostMapping("/index/queryGovBillborads")
    public PageResult<BillboardIndexDTO> queryGovBillborads(@RequestBody SearchGovRequest searchGovRequest);

    @PostMapping("/index/queryBizBillborads")
    PageResult<BillboardIndexDTO> queryBizBillborads(@RequestBody SearchBizRequest searchGovRequest);

    @PostMapping("/index/queryHarvests")
    PageResult<SearchHavestResponse> queryHarvests(@RequestBody SearchHarvestsRequest searchHarvestsRequest);

    @PostMapping("/index/queryTalents")
    PageResult<SearchTalentsResponse> queryTalents(@RequestBody SearchTalentsRequest searchTalentsRequest);

    @PostMapping("/index/queryEconomicMans")
    PageResult<SearchEconomicMansResponse> queryEconomicMans(@RequestBody SearchEconomicMansRequest searchTalentsRequest);

    @PostMapping("/index/queryNews")
    PageResult<SearchNewsResponse> queryNews(@RequestBody SearchNewsRequest searchNewsRequest);


}