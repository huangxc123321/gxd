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
 * @Date 2023/7/3 0003 17:27
 * @Version 2.0
 */
public interface HarvestApi {

    @PostMapping("/harvest/pageQuery")
    PageResult<HarvestResponse> pageQuery(@RequestBody HarvestRequest request);

    @PostMapping("/harvest/add")
    void add(@RequestBody HavestDTO havestDTO);

    @PostMapping("/harvest/update")
    void update(@RequestBody HavestDTO havestDTO);

    @PostMapping(value = "/harvest/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/harvest/getHarvesByHolder")
    List<HavestDTO> getHarvesByHolder(@RequestParam("holder") String holder);

    @GetMapping("/harvest/detail")
    HavestDetailInfo detail(@RequestParam("id") Long id);

    @GetMapping("/harvest/getCompanyById")
    HavestPO getHavestById(@RequestParam("id")  Long id);

    @GetMapping("/harvest/getRecommendHavest")
    List<RecommendHavestResponse> getRecommendHavest();

    @PostMapping("/harvest/pageMyHarvestQuery")
    PageResult<HarvestResponse> pageMyHarvestQuery(@RequestBody HarvestRequest request);

    @GetMapping("/havest/getContacts")
    List<String> getContacts(@RequestParam("contacts") String contacts);

    @GetMapping("/havest/getHoloder")
    List<String> getHoloder(@RequestParam("holder") String holder);

    @GetMapping("/havest/updateUnitName")
    void updateUnitName(@RequestParam("oldUnitName") String oldUnitName, @RequestParam("unitName") String unitName);
}
