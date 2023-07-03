package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 13:21
 * @Version 2.0
 */
public enum IsFixedEnum {

    // 是否定时发布 0 不定时  1定时
    WAIT(0, "不定时"),
    SIGNED(1, "定时"),

    ;

    private final Integer code;
    private final String desc;

    private IsFixedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }


    public static String getNameByIndex(Integer code) {
        for (IsFixedEnum targetEnum : IsFixedEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
