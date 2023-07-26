package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/20 0020 11:32
 * @Version 2.0
 */
public interface BillboardHarvestRelatedApi {

    @PostMapping("/billboard/harvest/related/add")
    void add(@RequestBody BillboardRelatedDTO billboardHarvestRelatedDTO);

    @PostMapping("/billboard/harvest/related/audit")
    void audit(@RequestBody BillboardRelatedAuditDTO billboardHarvestAuditDTO);

    @GetMapping("/billboard/harvest/getHarvestRecommend")
    List<BillboardHarvestRelatedResponse> getHarvestRecommend(@RequestParam("id") Long billboardId);

    @GetMapping("/billboard/harvest/getHarvestRecommendByHarvestId")
    List<HavestCollaborateDTO> getHarvestRecommendByHarvestId(@RequestParam("harvestId") Long harvestId);

    @GetMapping("/billboard/harvest/getHarvestByHarvestId")
    List<RelateBillboardDTO> getHarvestByHarvestId(@RequestParam("harvestId") Long harvestId);

    @GetMapping("/billboard/harvest/getBillboardstByHarvestId")
    List<BillboardHarvestRelatedResponse> getBillboardstByHarvestId(@RequestParam("harvestId") Long harvestId);
}
