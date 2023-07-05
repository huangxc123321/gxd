package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}
