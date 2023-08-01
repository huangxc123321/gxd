package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseDTO;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseRequest;
import com.gxa.jbgsw.business.protocol.dto.TechEconomicManAppraiseResponse;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 17:08
 * @Version 2.0
 */
public interface TechEconomicManAppraiseApi {

    @PostMapping("/tech/economic/appraise/add")
    void add(@RequestBody TechEconomicManAppraiseDTO techEconomicManAppraiseDTO);

    @PostMapping("/tech/economic/appraise/getAppraise")
    PageResult<TechEconomicManAppraiseResponse> getAppraise(@RequestBody TechEconomicManAppraiseRequest request);

}
