package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/31 0031 13:32
 * @Version 2.0
 */
public enum LevelEnum {
    // 等级：0 无， 1 金  2 银  3 铜
    _NULL(0, "无"),
    GOLD(1, "金"),
    SILVER(2, "银"),
    CUPRUM(3, "铜"),
    ;

    private final Integer code;
    private final String desc;

    private LevelEnum(Integer code, String desc) {
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
        for (LevelEnum targetEnum : LevelEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
