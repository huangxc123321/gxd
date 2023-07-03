package com.gxa.jbgsw.basis.client;

import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryRequest;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryValueQueryRequest;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.PageResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface DictionaryApi {

    @PostMapping("/user/add")
    public void saveOrUpdate(@RequestBody DictionaryDTO dictionaryDTO) throws BizException;

    @PostMapping("/dictionary/pageQuery")
    public PageResult<DictionaryResponse> pageQuery(@RequestBody DictionaryRequest request);

    @GetMapping("/dictionary/getDictionaryById")
    public DictionaryDTO getDictionaryById(@RequestParam("id") Long id);

    @PostMapping(value = "/dictionary/deleteBatchIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBatchIds(@RequestBody Long[] ids);

    @PostMapping(value = "/dictionary/getDictionaryByCodeAndTypeCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DictionaryDTO getDictionaryByCodeAndTypeCode(@RequestBody DictionaryValueQueryRequest request);

    @GetMapping("/dictionary/getByCache")
    public DictionaryDTO getByCache(@RequestParam("typeCode") String typeCode, @RequestParam("code") String code);
}
