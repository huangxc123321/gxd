package com.gxa.jbgsw.basis.controller;

import com.gxa.jbgsw.basis.client.WebsiteBottomApi;
import com.gxa.jbgsw.basis.entity.WebsiteBottom;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.errcode.BasisErrorCode;
import com.gxa.jbgsw.basis.service.WebsiteBottomService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api("字典管理")
public class WebsiteBottomController implements WebsiteBottomApi {
    @Resource
    WebsiteBottomService websiteBottomService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public void add(WebsiteBottomDTO websiteBottomDTO) throws BizException {
        WebsiteBottom websiteBottom = mapperFacade.map(websiteBottomDTO, WebsiteBottom.class);
        websiteBottomService.save(websiteBottom);
    }

    @Override
    public void update(WebsiteBottomDTO websiteBottomDTO) throws BizException {
        WebsiteBottom websiteBottom = websiteBottomService.getById(websiteBottomDTO.getId());
        if(websiteBottom == null){
            throw new BizException(BasisErrorCode.BASIS_PARAMS_ERROR);
        }

        // havestDTO有null就不需要替换harvest
        BeanUtils.copyProperties(websiteBottomDTO, websiteBottom, CopyPropertionIngoreNull.getNullPropertyNames(websiteBottom));
        websiteBottomService.updateById(websiteBottom);

    }

    @Override
    public WebsiteBottomDTO getWebsiteBottomById(Long id) throws BizException {
        WebsiteBottom websiteBottom = websiteBottomService.getById(id);

        WebsiteBottomDTO websiteBottomDTO = mapperFacade.map(websiteBottom, WebsiteBottomDTO.class);
        return websiteBottomDTO;
    }
}
