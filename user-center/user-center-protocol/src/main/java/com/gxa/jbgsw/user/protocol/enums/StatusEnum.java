package com.gxa.jbgsw.user.protocol.enums;

public enum StatusEnum {

    // 状态：0 正常， 1-删除
    NORMAL(0, "正常"),
    NOT_OPEN(1, "删除"),
    ;

    private final Integer code;
    private final String desc;

    private StatusEnum(Integer code, String desc) {
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
        for (StatusEnum targetEnum : StatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
