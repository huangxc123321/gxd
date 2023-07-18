package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 15:32
 * @Version 2.0
 */
public enum TechEconomicManLevelEnum {

    // 等级：0 无， 1 金  2 银  3 铜
    EMPTY(0, "无"),
    GOLD(1, "金"),
    SILVER(2, "银"),
    CUPRUM(3, "铜"),

    ;

    private final Integer code;
    private final String desc;

    private TechEconomicManLevelEnum(Integer code, String desc) {
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
        for (TechEconomicManLevelEnum targetEnum : TechEconomicManLevelEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
