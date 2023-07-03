package com.gxa.jbgsw.user.handler;

import com.gxa.jbgsw.common.exception.BizException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


@Component
@Primary
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = this.getError(webRequest);
        if (error instanceof BizException) {
            errorAttributes.put("code", ((BizException) error).getCode());
        }
        return errorAttributes;
    }
}
