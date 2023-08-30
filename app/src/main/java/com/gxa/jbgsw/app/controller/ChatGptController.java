package com.gxa.jbgsw.app.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.ChatGptDTO;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "AI聊天")
@RestController
@Slf4j
@ResponseBody
public class ChatGptController extends BaseController {
    private static final String OPENAI_API_KEY = "noDdgfuEzLWVREkuT3BlbkFJXuJGpUNF5kwZL5G6gvde";
    private static final String openai_api_url = "http";
    private static final String url = "https://api.openai.com/v1/chat";

    @ApiOperation("发送消息")
    @GetMapping("/chat/ai/send")
    ApiResult getCommentById(@RequestParam("msg") String msg) throws BizException, IOException {
        if(StrUtil.isBlank(msg)){
            throw new BizException(UserErrorCode.CHATGPT_MESSAGE_IS_NULL);
        }
        ApiResult apiResult = new ApiResult();

        try{
            ChatGptDTO chatGptDTO = new ChatGptDTO();
            chatGptDTO.setPrompt(msg);

            String jsonData = JSONObject.toJSONString(chatGptDTO);
            ByteArrayEntity data = new ByteArrayEntity(jsonData.getBytes("UTF-8"));
            // 创建HttpClient和HttpPost对象
            PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
            cm.setMaxTotal(100);
            HttpClient httpClient = new DefaultHttpClient(cm);
            HttpPost httpPost = new HttpPost(url);

            // 设置请求的头信息
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer "+OPENAI_API_KEY);
            // 设置请求的数据
            httpPost.setEntity(data);
            // 发送HTTP请求并获取响应结果
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "UTF-8");

            System.out.println("chatGpt 返回： "+response);


            apiResult.setData(response);

        }catch (Exception ex){
            ex.printStackTrace();
        }


        return apiResult;
    }

}
