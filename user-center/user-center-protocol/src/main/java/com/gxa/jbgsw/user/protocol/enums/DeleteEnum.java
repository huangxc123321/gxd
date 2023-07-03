package com.gxa.jbgsw.user.protocol.enums;

public enum DeleteEnum {
    // 逻辑已删除值(默认为 1), 0 未删除
    NOT_DELETE(0, "未删除"),
    HAVE_DELETE(1, "已删除"),
    ;

    private final Integer code;
    private final String desc;

    private DeleteEnum(Integer code, String desc) {
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
        for (DeleteEnum targetEnum : DeleteEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
