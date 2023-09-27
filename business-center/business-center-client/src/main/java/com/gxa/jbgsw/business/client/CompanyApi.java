package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CompanyDTO;
import com.gxa.jbgsw.business.protocol.dto.CompanyPCResponse;
import com.gxa.jbgsw.business.protocol.dto.CompanyRequest;
import com.gxa.jbgsw.business.protocol.dto.CompanyResponse;
import com.gxa.jbgsw.common.utils.PageResult;
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
    CompanyResponse getCompanyById(@RequestParam("id") Long id);

    @PostMapping("/company/pageQuery")
    PageResult<CompanyResponse> pageQuery(@RequestBody CompanyRequest request);

    @GetMapping("/company/getCompanyById4Pc")
    CompanyPCResponse getCompanyById4Pc(@RequestParam("id") Long id);

    @GetMapping("/company/getCompanyByUnitName")
    CompanyDTO getCompanyByUnitName(@RequestParam("unitName") String unitName);

    @GetMapping("/company/getCompanyByName")
    CompanyPCResponse getCompanyByName(@RequestParam("name") String name);

    @GetMapping("/company/updateUnitName")
    void updateUnitName(@RequestParam("oldUnitName") String oldUnitName, @RequestParam("unitName") String unitName);
}
