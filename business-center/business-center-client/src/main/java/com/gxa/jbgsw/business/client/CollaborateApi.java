package com.gxa.jbgsw.business.client;

import com.gxa.jbgsw.business.protocol.dto.CollaborateDTO;
import com.gxa.jbgsw.business.protocol.dto.HavestCollaborateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mac on 2023/7/24.
 */
public interface CollaborateApi {
    @PostMapping("/collaborate/add")
    void add(@RequestBody CollaborateDTO collaborateDTO);

    @GetMapping("/collaborate/getHavestCollaborates")
    List<HavestCollaborateDTO> getHavestCollaborates(@RequestParam("havestId") Long id);
}
