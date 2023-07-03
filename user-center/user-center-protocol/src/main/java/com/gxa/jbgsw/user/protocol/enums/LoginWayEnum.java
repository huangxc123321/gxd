package com.gxa.jbgsw.user.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/6/22 0022 17:06
 * @Version 2.0
 */
public enum LoginWayEnum {

    //登录方式
    ACCOUNT_PASSWORD (0, "账号+密码"),
    ACCOUNT_VALIDATECODE(1, "账号+验证码"),
    SCAN_QR_TOKEN (2, "扫描二维码登录"),
    ;

    private final Integer code;
    private final String desc;

    private LoginWayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getNameByIndex(Integer code) {
        for (LoginWayEnum targetEnum : LoginWayEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
