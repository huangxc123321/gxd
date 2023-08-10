package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
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

    @GetMapping("/billboard/economic/getMyEconomicMan")
    MyBillboardEconomicManDTO getMyEconomicMan(@RequestParam("id") Long billboardId);

    @GetMapping("/billboard/economic/getById")
    BillboardEconomicRelatedDTO getById(@RequestParam("id") Long id);

    @PostMapping("/message/update/requireStatus")
    void updateRequireStatus(@RequestBody AppRequiresAccepptDTO requiresAccepptDTO);
}
