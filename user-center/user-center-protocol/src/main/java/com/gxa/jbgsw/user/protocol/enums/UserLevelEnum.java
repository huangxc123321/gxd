package com.gxa.jbgsw.user.protocol.enums;

public enum UserLevelEnum {
    // 用户级别：0 普通管理员 1 维护人员  2超级管理员
    COMMON(0, "普通管理员"),
    MAINTAINER(1, "施工人员"),
    SUPPER(2, "超级管理员"),
    CHECK(3, "检测人员（生产检测平台使用）"),
    ;

    private final Integer code;
    private final String desc;

    private UserLevelEnum(Integer code, String desc) {
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
        for (UserLevelEnum targetEnum : UserLevelEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
