package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/20 0020 14:30
 * @Version 2.0
 */
public interface BillboardTalentRelatedApi {

    @PostMapping("/billboard/talent/related/add")
    void add(@RequestBody BillboardRelatedDTO billboardHarvestRelatedDTO);

    @PostMapping("/billboard/talent/related/audit")
    void audit(@RequestBody BillboardRelatedAuditDTO billboardHarvestAuditDTO);

    @GetMapping("/billboard/talent/getTalentRecommend")
    List<BillboardTalentRelatedResponse> getTalentRecommend(@RequestParam("id") Long billboardId);

    @GetMapping("/billboard/talent/getBillboardRecommendByTalentIdd")
    List<HarvestBillboardRelatedDTO> getBillboardRecommendByTalentId(@RequestParam("id")Long id);

    @GetMapping("/billboard/talent/getCollaborateByTalentId")
    List<HavestCollaborateDTO> getCollaborateByTalentId(@RequestParam("id")Long id);

    @GetMapping("/billboard/talent/getMyBillboradCollaborate")
    List<MyBillboradCollaborateResponse> getMyBillboradCollaborate(@RequestParam("talentId")Long talentId);
}
