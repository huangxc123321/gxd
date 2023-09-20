package com.gxa.jbgsw.user.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/9/15 0015 11:04
 * @Version 2.0
 */
public enum UserPlatformEnum {

    // 平台类型： 0 app , 1 后台
    APP(0, "app"),
    ADMIN(1, "后台"),
    ;

    private final Integer code;
    private final String desc;

    private UserPlatformEnum(Integer code, String desc) {
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
        for (UserPlatformEnum targetEnum : UserPlatformEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
