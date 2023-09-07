package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    List<TechnicalFieldClassifyPO> getAllById(@RequestParam("pid") Long pid);

    @GetMapping("/techical/field/classify/getById")
    TechnicalFieldClassifyDTO getById(@RequestParam("id") Long id);

    @PostMapping("/techical/field/classify/insert")
    Long insert(@RequestBody TechnicalFieldClassifyPO po1);

    @GetMapping("/techical/field/classify/getAllByParentId")
    List<TechnicalFieldClassifyDTO> getAllByParentId(@RequestParam("pid") Long pid);
}
