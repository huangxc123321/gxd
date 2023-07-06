package com.gxa.jbgsw.app.config;

import com.alibaba.fastjson.JSON;
import com.gxa.jbgsw.common.dto.ExceptionInfo;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ResultCode;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Slf4j
@Configuration
public class KeepErrMsgConfiguration extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);

        if (exception instanceof RetryableException) {
            return exception;
        }

        try {
            if (exception instanceof FeignException && ((FeignException) exception).responseBody().isPresent()) {
                ByteBuffer responseBody = ((FeignException) exception).responseBody().get();
                String bodyText = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();
                ExceptionInfo exceptionInfo = JSON.parseObject(bodyText, ExceptionInfo.class);
                Integer code = Optional.ofNullable(exceptionInfo.getCode()).orElse(-1);
                String message = Optional.ofNullable(exceptionInfo.getMessage()).orElse("网络异常，请稍后再试！");

                ResultCode resultCode = new ResultCode(code, message);
                return new BizException(resultCode);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;

    }


}
