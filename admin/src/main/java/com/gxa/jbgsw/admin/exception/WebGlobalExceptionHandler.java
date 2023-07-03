package com.gxa.jbgsw.admin.exception;


import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class WebGlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResult<?> exceptionHandler(Exception exception) {
        String message = "系统异常，请稍后再试！";
        log.error(exception.getMessage(), exception);
        ApiResult<?> apiResult = new ApiResult<>();
        apiResult.setCode(-1);
        apiResult.setMessage(message+" 异常内容："+exception.getMessage());
        return apiResult;
    }

    @ExceptionHandler({BizException.class})
    public ApiResult<?> businessExceptionHandler(BizException exception) {
        log.error(exception.getMessage(), exception);
        ApiResult<?> apiResult = new ApiResult<>();
        apiResult.setCode(exception.getResultCode().getCode());
        apiResult.setMessage(exception.getResultCode().getMsg());
        return apiResult;
    }

}
