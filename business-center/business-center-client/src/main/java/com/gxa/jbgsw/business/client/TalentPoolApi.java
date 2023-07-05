package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolRequest;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolResponse;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
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
    void add(@RequestBody TalentPoolDTO talentPoolDTO);

    @PostMapping("/talent/pool/update")
    void update(@RequestBody TalentPoolDTO talentPoolDTO);

    @PostMapping("/talent/pool/getTalentPoolByTech")
    List<TalentPoolDTO> getTalentPoolByTech(@RequestParam("key") String key);
}
