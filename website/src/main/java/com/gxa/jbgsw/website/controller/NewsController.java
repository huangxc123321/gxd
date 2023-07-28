package com.gxa.jbgsw.website.controller;

import com.gxa.jbgsw.business.protocol.dto.NewsResponse;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.website.feignapi.NewsFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "新闻资讯管理")
@RestController
@Slf4j
@ResponseBody
public class NewsController extends BaseController {
    @Resource
    NewsFeignApi newsFeignApi;

    @ApiOperation("获取热点政策资讯")
    @GetMapping("/news/getHotNews")
    List<NewsResponse> getHotNews(){
        // 先时间，阅览数排序， 取五条数据
        return newsFeignApi.getHotNews();
    }


}
