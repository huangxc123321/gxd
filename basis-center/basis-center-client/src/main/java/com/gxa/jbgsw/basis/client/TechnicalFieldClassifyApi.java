package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Mr. huang
 * @Date 2023/7/27 0027 16:18
 * @Version 2.0
 */
public interface TechnicalFieldClassifyApi {
    @GetMapping("/techical/field/classify/getAll")
    List<TechnicalFieldClassifyDTO> getAll();

    @GetMapping("/techical/field/classify/getAllById")
    List<TechnicalFieldClassifyDTO> getAllById(@RequestParam("pid") Long pid);

}
