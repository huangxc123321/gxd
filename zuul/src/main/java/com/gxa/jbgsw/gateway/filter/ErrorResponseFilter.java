package com.gxa.jbgsw.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@ConditionalOnProperty(name="zuul.SendErrorFilter.error.disable")
public class ErrorResponseFilter extends SendErrorFilter {
    Logger logger = LoggerFactory.getLogger(ErrorResponseFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        try {
            int responseStatusCode = ctx.getResponseStatusCode();
            // 此处自定义响应体start
            String cumstomBody = "{}";//内容省略...
            // 此处自定义响应体end
            response.setStatus(ctx.getResponseStatusCode());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(cumstomBody.getBytes());
        } catch (IOException e) {
            ReflectionUtils.rethrowRuntimeException(e);
        } finally {
            System.out.println("over...");
        }
        return null;

    }
}
