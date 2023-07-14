package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.common.exception.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface WebsiteBottomApi {

    @PostMapping("/website/bottom/add")
    public void add(@RequestBody WebsiteBottomDTO websiteBottomDTO) throws BizException;

    @PostMapping("/website/bottom/update")
    public void update(@RequestBody WebsiteBottomDTO websiteBottomDTO) throws BizException;

    @GetMapping("/website/bottom/getWebsiteBottomInfo")
    public WebsiteBottomDTO getWebsiteBottomInfo() throws BizException;

}
