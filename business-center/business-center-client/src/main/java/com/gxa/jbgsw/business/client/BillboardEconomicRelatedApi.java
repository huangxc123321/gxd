package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.BillboardEconomicRelatedResponse;
import com.gxa.jbgsw.business.protocol.dto.BillboardRelatedAuditDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardRelatedDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTalentRelatedResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangxc
 */
@RestController
@Api(tags = "榜单与经纪人之间关联管理")
public interface BillboardEconomicRelatedApi {

    @PostMapping("/billboard/economic/related/add")
    void add(@RequestBody BillboardRelatedDTO billboardRelatedDTO);

    @PostMapping("/billboard/economic/related/audit")
    void audit(@RequestBody BillboardRelatedAuditDTO billboardRelatedAuditDTO);

    @GetMapping("/billboard/economic/getEconomicRecommend")
    List<BillboardEconomicRelatedResponse> getEconomicRecommend(@RequestParam("id") Long billboardId);

}
