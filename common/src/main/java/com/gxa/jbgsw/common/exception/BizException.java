package com.gxa.jbgsw.common.exception;

import com.gxa.jbgsw.common.utils.ResultCode;


public class BizException extends RuntimeException {

    private static final long serialVersionUID = -2909748528316703603L;

    private ResultCode resultCode;

    public BizException() {
        super();
    }

    public BizException(ResultCode resultCode, String... args) {
        super(resultCode.parseMessage(args));
        this.resultCode = resultCode;
    }

    public Integer getCode() {
        return this.resultCode.getCode();
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "BizException{" +
                "code=" + this.getCode() + ", " +
                "message=" + this.getMessage() + "}";
    }


}
