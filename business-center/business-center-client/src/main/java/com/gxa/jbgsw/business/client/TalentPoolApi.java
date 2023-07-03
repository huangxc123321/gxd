package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.TalentPoolDTO;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolRequest;
import com.gxa.jbgsw.business.protocol.dto.TalentPoolResponse;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TalentPoolApi {

    @PostMapping(value = "/business/talent/pool/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/business/talent/pool/pageQuery")
    PageResult<TalentPoolResponse> pageQuery(@RequestBody TalentPoolRequest request);

    @PostMapping("/business/talent/pool/add")
    void add(@RequestBody TalentPoolDTO talentPoolDTO);

    @PostMapping("/business/talent/pool/update")
    void update(TalentPoolDTO talentPoolDTO);
}
