package com.gxa.jbgsw.admin.controller;

import com.gxa.jbgsw.admin.feignapi.WebsiteBottomFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.WebsiteBottomDTO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "网站底部设置")
@RestController
@Slf4j
@ResponseBody
public class WebsiteBottomController extends BaseController {
    @Resource
    WebsiteBottomFeignApi websiteBottomFeignApi;

    @ApiOperation("新增网站底部信息")
    @PostMapping("/website/bottom/add")
    public void add(@RequestBody WebsiteBottomDTO websiteBottomDTO) throws BizException{
        websiteBottomDTO.setCreateBy(this.getUserId());
        websiteBottomDTO.setCreateAt(new Date());

        websiteBottomFeignApi.add(websiteBottomDTO);
    }

    @ApiOperation("更新网站底部信息")
    @PostMapping("/website/bottom/update")
    public void update(@RequestBody WebsiteBottomDTO websiteBottomDTO) throws BizException{
        websiteBottomDTO.setUpdateBy(this.getUserId());
        websiteBottomDTO.setUpdateAt(new Date());

        websiteBottomFeignApi.update(websiteBottomDTO);
    }

    @GetMapping("/website/bottom/getWebsiteBottomById")
    @ApiOperation("获取网站底部信息")
    public WebsiteBottomDTO getWebsiteBottomById(@RequestParam("id") Long id) throws BizException{
        return websiteBottomFeignApi.getWebsiteBottomById(id);
    }


}
