package com.gxa.jbgsw.business.client;


import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardResponse;
import com.gxa.jbgsw.business.protocol.dto.DetailInfoDTO;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxc
 */
public interface BillboardApi {

    @PostMapping("/billboard/add")
    void add(@RequestBody BillboardDTO billboardDTO);

    @PostMapping(value = "/billboard/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @GetMapping("/billboard/cancel/top")
    void cancelTop(@RequestParam("id") Long id);

    @PostMapping(value = "/billboard/batchIdsTop",consumes = MediaType.APPLICATION_JSON_VALUE)
    void batchIdsTop(Long[] ids);

    @PostMapping("/billboard/pageQuery")
    PageResult<BillboardResponse> pageQuery(@RequestBody BillboardRequest request);

    @GetMapping("/billboard/updateSeqNo")
    void updateSeqNo(@RequestParam("id") Long id, @RequestParam("seqNo") Integer seqNo);

    @GetMapping("/billboard/detail")
    DetailInfoDTO detail(@RequestParam("id") Long id);
}
