package com.gxa.jbgsw.basis.client;


import com.gxa.jbgsw.basis.protocol.dto.*;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DictionaryTypeApi {

    @PostMapping("/dictionayType/pageQuery")
    public PageResult<DictionaryTypeResponse> pageQuery(@RequestBody DictionaryTypePageRequest request);

    @PostMapping("/dictionayType/list")
    public List<DictionaryTypeResponse> list(@RequestBody DictionaryTypeRequest request);

    @PostMapping("/dictionayType/getDictionaryByCode")
    public List<DictionaryResponse> getDictionaryByCode(@RequestBody DictionaryCodeRequest request);

    @GetMapping("/dictionayType/getByCode")
    public List<DictionaryResponse> getByCode(@RequestParam(value = "code") String code);
}
