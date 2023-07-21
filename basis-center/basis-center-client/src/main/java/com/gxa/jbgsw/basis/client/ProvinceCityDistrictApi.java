package com.gxa.jbgsw.basis.client;


import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryRequest;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryResponse;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProvinceCityDistrictApi {

    @PostMapping("/address/list")
    public PageResult<ProvinceCityDistrictQueryResponse> list(@RequestBody ProvinceCityDistrictQueryRequest request);

    @GetMapping("/address/sonlist")
    public List<ProvinceCityDistrictQueryResponse> sonlist(@RequestParam("pid") String pid);

    @GetMapping("/address/getProvinceCityDistrictById")
    public ProvinceCityDistrictVO getProvinceCityDistrictById(@RequestParam("id") Integer id);

    @GetMapping("/address/getProvinceCityDistrictByName")
    public ProvinceCityDistrictVO getProvinceCityDistrictByName(@RequestParam("name") String name);
}
