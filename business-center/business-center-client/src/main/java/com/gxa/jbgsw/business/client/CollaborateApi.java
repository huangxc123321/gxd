package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import java.util.List;

/**
 * Created by mac on 2023/7/24.
 */
public interface CollaborateApi {
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO);

    @GetMapping("/collaborate/cancel")
    void cancel(@RequestParam("id") Long id);

    @GetMapping("/collaborate/getHavestCollaborates")
    List<HavestCollaborateDTO> getHavestCollaborates(@RequestParam("havestId") Long id);

    @GetMapping("/collaborate/getHarvestCollaborateDetail")
    CollaborateHavrestDetailDTO getHarvestCollaborateDetail(@RequestParam("id") Long id);

//    @GetMapping("/collaborate/getTalentCollaborateDetail")
//    CollaborateTaleDetailDTO getTalentCollaborateDetail(@RequestParam("id") Long id);

    @GetMapping("/collaborate/getById")
    CollaborateDTO getById(@RequestParam("id") Long id);

    @PostMapping("/collaborate/pageQuery")
    PageResult pageQuery(@RequestBody MyCollaborateRequest request);

    @PostMapping("/collaborate/apply")
    void apply(@RequestBody MyCollaborateApplyDTO collaborateApply);
}
