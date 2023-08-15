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
 * @Author Mr. huang
 * @Date 2023/7/4 0004 9:27
 * @Version 2.0
 */
public interface TechEconomicManApi {

    @PostMapping(value = "/tech/broker/getTechEconomicByLabel",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<HavestDTO> getTechEconomicByLabel(@RequestBody String[] labels);

    @PostMapping(value = "/tech/broker/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/tech/broker/add")
    void add(@RequestBody TechEconomicManDTO techEconomicManDTO);

    @PostMapping("/tech/broker/update")
    void update(@RequestBody TechEconomicManDTO techEconomicManDTO);

    @PostMapping("/tech/broker/pageQuery")
    PageResult<TechEconomicManResponse> pageQuery(@RequestBody TechEconomicManRequest request);

    @GetMapping("/tech/broker/getTechEconomicManById")
    TechEconomicManResponse getTechEconomicManById(@RequestParam(value = "id") Long id);

    @GetMapping("/tech/broker/getLabels")
    List<String> getLabels();

    @PostMapping("/tech/broker/getEconomicManRequires")
    MyOrderResponse getEconomicManRequires(@RequestBody TechEconomicManRequiresRequest request);

    @PostMapping("/tech/broker/queryEconomicManRequires")
    PageResult<TechEconomicManRequiresResponse> queryEconomicManRequires(@RequestBody TechEconomicManRequiresRequest request);
}
