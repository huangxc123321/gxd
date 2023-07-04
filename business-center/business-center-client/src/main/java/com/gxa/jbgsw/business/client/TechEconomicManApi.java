package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.HavestDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/4 0004 9:27
 * @Version 2.0
 */
public interface TechEconomicManApi {

    @PostMapping(value = "/harvest/getTechEconomicByLabel",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<HavestDTO> getTechEconomicByLabel(@RequestBody String[] labels);

    @PostMapping(value = "/business/talent/pool/deleteBatchIds",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

}
