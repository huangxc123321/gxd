package com.gxa.jbgsw.user.client;

import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/7/4 0004 13:11
 * @Version 2.0
 */
public interface CompanyApi {

    @PostMapping("/company/add")
    void add(@RequestBody CompanyDTO companyDTO);

    @PostMapping(value = "/company/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping("/company/update")
    void update(@RequestBody CompanyDTO companyDTO);

    @GetMapping("/company/getCompanyById")
    CompanyDTO getCompanyById(@RequestParam("id") Long id);

    @PostMapping("/company/pageQuery")
    PageResult<CompanyResponse> pageQuery(@RequestBody CompanyRequest request);

}
