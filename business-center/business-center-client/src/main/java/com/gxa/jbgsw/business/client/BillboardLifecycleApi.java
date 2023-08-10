package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardlifecycleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/8/10 0010 17:58
 * @Version 2.0
 */
public interface BillboardLifecycleApi {

    @PostMapping("/billboard/lifecycle/add")
    void add(@RequestBody BillboardlifecycleDTO billboardlifecycleDTO);

    @GetMapping("/billboard/lifecycle/list")
    List<BillboardlifecycleResponse> list(@RequestParam("pid") Long pid);


}
