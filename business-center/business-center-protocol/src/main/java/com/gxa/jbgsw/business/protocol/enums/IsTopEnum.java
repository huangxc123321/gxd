package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/5 0005 13:35
 * @Version 2.0
 */
public enum IsTopEnum {

    // 是否置顶： 0 不置顶 1 置顶
    NO_TOP(0, "不置顶"),
    TOP(1, "置顶"),
    ;

    private final Integer code;
    private final String desc;

    private IsTopEnum(Integer code, String desc) {
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
        for (IsTopEnum targetEnum : IsTopEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
