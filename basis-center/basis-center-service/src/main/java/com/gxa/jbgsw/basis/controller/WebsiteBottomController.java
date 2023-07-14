package com.gxa.jbgsw.basis.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.basis.client.WebsiteBottomApi;
import com.gxa.jbgsw.basis.entity.WebsiteBottom;
import com.gxa.jbgsw.basis.protocol.dto.FriendlyLinkDTO;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.basis.protocol.errcode.BasisErrorCode;
import com.gxa.jbgsw.basis.service.WebsiteBottomService;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.CopyPropertionIngoreNull;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
        websiteBottom.setLinks(JSONObject.toJSONString(websiteBottomDTO.getFriendlyLinks()));
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
        if(websiteBottomDTO.getFriendlyLinks() != null && websiteBottomDTO.getFriendlyLinks().size()> 0){
            websiteBottom.setLinks(JSONObject.toJSONString(websiteBottomDTO.getFriendlyLinks()));
        }

        websiteBottomService.updateById(websiteBottom);
    }

    @Override
    public WebsiteBottomDTO getWebsiteBottomInfo() throws BizException {
        List<WebsiteBottom> list = websiteBottomService.list();
        if(CollectionUtils.isNotEmpty(list)){
            WebsiteBottom websiteBottom = list.get(0);
            WebsiteBottomDTO websiteBottomDTO = mapperFacade.map(websiteBottom, WebsiteBottomDTO.class);

            String links = websiteBottom.getLinks();
            System.out.println("---------------------> "+links);
            if(StrUtil.isNotBlank(links)){
                List<FriendlyLinkDTO> friendlyLinkDTOS = JSONArray.parseArray(links,FriendlyLinkDTO.class);
                websiteBottomDTO.setFriendlyLinks(friendlyLinkDTOS);
            }
            return websiteBottomDTO;
        }
        return new WebsiteBottomDTO();
    }
}
