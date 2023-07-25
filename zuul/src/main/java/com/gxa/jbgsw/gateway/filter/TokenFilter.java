package com.gxa.jbgsw.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class TokenFilter extends ZuulFilter {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        System.out.println(request.getRequestURI()); ///apigateway/product/api/v1/product/list
        System.out.println(request.getRequestURL()); //http://localhost:9000/apigateway/product/api/v1/product/list

        //进行拦截，就会进入下面的 run方法中, app 除首页接口其它都需要登录
        if (request.getRequestURI().contains("/web")){
            if(request.getRequestURI().contains("/index/addBillboardGain")){
                log.info("应用需要验证是否登录，web 几个链接需要登录");
                return true;
            }
            log.info("应用登录不需验证");
            return false;
        }else if (request.getRequestURI().contains("/admin")){
            if(request.getRequestURI().contains("/login")
                    || request.getRequestURI().contains("/captcha")){
                log.info("应用登录不需验证");
                return false;
            }
            log.info("应用需要验证是否登录");
            return true;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext requestContext =  RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        String uri = requestContext.getRequest().getRequestURI();
        //token对象
        String token = request.getHeader("token");

        //token为空，就不能访问
        if((StrUtil.isNotBlank(uri) && uri.contains("portal/nul"))){
            return null;
        }

        else if (StringUtils.isBlank(token)) {
            //停止访问，并返回出错的消息
            throwError(requestContext, response, 1008, "账号未登录或者登录已过期");
        }else{
            // 不为空，看redis是否存在
            String key = RedisKeys.USER_TOKEN + token;
            String userId = stringRedisTemplate.opsForValue().get(key);
            if(StrUtil.isEmpty(userId)){
                throwError(requestContext, response,1008, "账号未登录或者登录已过期");
            }

            String k = RedisKeys.USER_INFO + userId;
            String userInfo = stringRedisTemplate.opsForValue().get(k);
            if(StrUtil.isEmpty(userInfo)){
                throwError(requestContext, response, 1008, "账号未登录或者登录已过期");
            }
        }


        //正常的话，继续向下走
        return null;
    }

    private void throwError(RequestContext requestContext,HttpServletResponse response, Integer code, String msg){
        //停止访问，并返回出错的消息
        requestContext.setSendZuulResponse(false);
        ApiResult<Object> apiResult = new ApiResult<>(code, msg);

        //防止中文乱码
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseBody(JSONObject.toJSONString(apiResult));
        response.setHeader("Content-type", "text/html;charset=UTF-8");

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] dataByteArr = JSONObject.toJSONString(apiResult).getBytes();
            outputStream.write(dataByteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }


}
