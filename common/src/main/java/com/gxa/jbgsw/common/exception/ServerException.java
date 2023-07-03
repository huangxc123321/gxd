package com.gxa.jbgsw.common.exception;


import com.gxa.jbgsw.common.utils.ResultCode;

public class ServerException extends RuntimeException  {

    private static final long serialVersionUID = -3148029864455572418L;

    private ResultCode resultCode;

    public ServerException() {
        super();
    }

    public ServerException(ResultCode resultCode, String... args) {
        super(getFormatedMsg(resultCode, args));
        this.resultCode = resultCode;
    }

    private static String getFormatedMsg(ResultCode resultCode, String... args) {
        String msg = resultCode.getMsg();
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        return msg;
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
        return "ServerException{" +
                "code=" + this.getCode() + ", " +
                "message=" + this.getMessage() + "}";
    }



}
