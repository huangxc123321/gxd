package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxc
 */
public interface LastBillboardSetApi {

    @PostMapping("/last/billboard/pageQuery")
    PageResult<LastBillboardResponse> pageQuery(@RequestBody LastBillboardRequest request);

    @PostMapping(value = "/last/billboard/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/last/billboard/cancel/top")
    void cancelTop(@RequestParam("id") Long id);

    @GetMapping("/last/billboard/set/top")
    void top(@RequestParam("id") Long id);

    @GetMapping("/last/billboard/detail")
    DetailInfoDTO detail(@RequestParam("id") Long id);

}
