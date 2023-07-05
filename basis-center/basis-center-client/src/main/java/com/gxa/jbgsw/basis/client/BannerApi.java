package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.BannerDTO;
import com.gxa.jbgsw.basis.protocol.dto.BannerRequest;
import com.gxa.jbgsw.basis.protocol.dto.BannerResponse;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Mr. huang
 * @Date 2023/7/4 0004 16:37
 * @Version 2.0
 */
public interface BannerApi {

    @PostMapping(value = "/banner/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping(value = "/banner/add",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody BannerDTO[] banners) throws BizException;

    @PostMapping("/banner/update")
    public void update(@RequestBody BannerDTO bannerDTO) throws BizException;

    @GetMapping("/banner/getBannerById")
    public BannerDTO getBannerById(@RequestParam("id") Long id) throws BizException;

    @GetMapping("/banner/updateSeqNo")
    void updateSeqNo(@RequestParam("id") Long id, @RequestParam("seqNo") Integer seqNo);

    @GetMapping("/banner/updateStatus")
    void updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status);

    @PostMapping("/banner/pageQuery")
    PageResult<BannerResponse> pageQuery(@RequestBody BannerRequest request);

}
