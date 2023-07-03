package com.gxa.jbgsw.common.utils;

import java.io.Serializable;

public class ResultCode implements Serializable {
    private Integer code;

    private String msg;

    public ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String parseMessage(Object... args) {
        if (args != null && args.length > 0) {
            return String.format(msg, args);
        }
        return msg;
    }

}
