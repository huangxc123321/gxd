package com.gxa.jbgsw.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.gateway.errcode.CommonResultCode;
import com.gxa.jbgsw.gateway.handler.AuthorizeConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

@Component
@RefreshScope
@Slf4j
public class AuthorizedFilter extends ZuulFilter {
    public static final String TOKEN_HEADER_DEPRECATED = "token";
    public static final String EXEC_FLAG = "AUTHORIZE_FILTER_EXEC_FLAG";

    @Autowired
    private AuthorizeConfig authorizeConfig;
    PathMatcher matcher = new AntPathMatcher();


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = currentContext.getRequest();
        String uri = httpServletRequest.getRequestURI();

        String matchedPath = authorizeConfig.getPaths().stream().filter(t -> matcher.match(t, uri)).findFirst().orElse(null);
        if(matchedPath == null) {
            matchedPath = authorizeConfig.getOptionalPaths().stream().filter(t -> matcher.match(t, uri)).findFirst().orElse(null);
        }

        if (matchedPath != null) {
            // 设置过滤标识，以便后续拦截器使用
            currentContext.set(EXEC_FLAG);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object run() throws ZuulException {
        // 逻辑处理
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 如果是登录、获取图形验证码等不需要验证的可以直接放行
        String uri = request.getRequestURI();
        // 从头中获取token
        String token = request.getHeader(TOKEN_HEADER_DEPRECATED);
        //如果没有提供凭证，判断目标资源的认证要求是否为可选
        if(StringUtils.isEmpty(token)) {
            doFail(context);
        }

        return null;
    }


    private void doFail(RequestContext context) {
        ApiResult<?> apiResult = new ApiResult<>();
        apiResult.setCode(CommonResultCode.UNLOGIN.getCode());
        apiResult.setMessage(CommonResultCode.UNLOGIN.getMsg());

        context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        context.setResponseBody(JSONObject.toJSONString(apiResult));
        context.getResponse().setContentType("application/json;charset=utf-8");
        context.setSendZuulResponse(false);
    }

}
