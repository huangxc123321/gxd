package com.gxa.jbgsw.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.admin.feignapi.NewsFeignApi;
import com.gxa.jbgsw.admin.feignapi.UserFeignApi;
import com.gxa.jbgsw.business.protocol.dto.*;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "新闻管理")
@RestController
@Slf4j
@ResponseBody
public class NewsController extends BaseController {
    @Resource
    NewsFeignApi newsFeignApi;
    @Resource
    UserFeignApi userFeignApi;

    @ApiOperation(value = "新增新闻", notes = "新增新闻")
    @PostMapping("/new/add")
    void add(@RequestBody NewsDTO newsDTO){
        newsDTO.setCreateBy(this.getUserId());
        newsFeignApi.add(newsDTO);
    }

    @ApiOperation(value = "批量删除新闻", notes = "批量删除新闻")
    @PostMapping("/news/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        newsFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "新闻ID", name = "id", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/news/detail")
    public NewsDTO detail(@RequestParam("id")Long id){
        NewsDTO newsDTO = newsFeignApi.detail(id);
        UserResponse userResponse = userFeignApi.getUserById(newsDTO.getCreateBy());
        if(userResponse != null){
            if(StrUtil.isNotBlank(userResponse.getUnitName())){
                newsDTO.setCreateName(userResponse.getUnitName());
            }else{
                newsDTO.setCreateName(userResponse.getNick());
            }
        }
        return newsDTO;
    }

    @ApiOperation("获取新闻列表")
    @PostMapping("/news/pageQuery")
    PageResult<NewsResponse> pageQuery(@RequestBody NewsRequest request){
        PageResult<NewsResponse> pageResult = newsFeignApi.pageQuery(request);
        log.info("Result：{}", JSONObject.toJSONString(pageResult));

        return pageResult;
    }

    @ApiOperation(value = "修改新闻", notes = "修改新闻")
    @PostMapping("/news/update")
    public void update(@RequestBody NewsDTO newsDTO){
        newsDTO.setUpdateBy(this.getUserId());
        newsFeignApi.update(newsDTO);
    }



}
