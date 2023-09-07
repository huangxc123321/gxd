package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TalentPoolApi {

    @PostMapping(value = "/talent/pool/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/talent/pool/pageQuery")
    PageResult<TalentPoolResponse> pageQuery(@RequestBody TalentPoolRequest request);

    @PostMapping("/talent/pool/add")
    void add(@RequestBody TalentPoolPO talentPoolPO);

    @PostMapping("/talent/pool/update")
    void update(@RequestBody TalentPoolPO talentPoolPO);

    @PostMapping("/talent/pool/getTalentPoolByTech")
    List<TalentPoolDTO> getTalentPoolByTech(@RequestParam("key") String key);

    @GetMapping("/talent/pool/getTalentPoolDetailInfo")
    TalentPoolDetailInfo getTalentPoolDetailInfo(@RequestParam("id") Long id);

    @GetMapping("/talent/pool/getTalentPoolById")
    TalentPoolDTO getTalentPoolById(@RequestParam("id") Long id);

    @PostMapping("/talent/pool/updateStatus")
    void updateStatus(@RequestBody TalentPoolAuditingDTO talentPoolAuditingDTO);

    @GetMapping("/talent/pool/getUnits")
    List<String> getUnits(@RequestParam("unitName") String unitName);
}
