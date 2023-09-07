package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.BillboardGainAddDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainAuditDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardGainResponse;
import com.gxa.jbgsw.common.exception.BizException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 16:08
 * @Version 2.0
 */
public interface BillboardGainApi {


    /**
     *  通过榜单ID，获取该榜单的揭榜信息
     */
    @GetMapping("/billboard/gain/getBillboardGainByPid")
    List<BillboardGainDTO> getBillboardGainByPid(@RequestParam("id") Long id);

    @GetMapping("/billboard/gain/getBillboardGainById")
    BillboardGainDTO getBillboardGainById(@RequestParam("id") Long id);

    @PostMapping(value = "/billboard/updateadd")
    public void update(@RequestBody BillboardGainAuditDTO billboardGainAuditDTO) throws BizException;

    @PostMapping(value = "/billboard/gain/add")
    void addBillboardGain(@RequestBody BillboardGainAddDTO billboardGainAddDTO);

    @GetMapping("/billboard/gain/getIsGain")
    boolean getIsGain(@RequestParam("pid") Long pid, @RequestParam("userId") Long userId);
}
