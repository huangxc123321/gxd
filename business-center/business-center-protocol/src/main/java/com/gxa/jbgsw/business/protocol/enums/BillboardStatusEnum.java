package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/6/29 0029 17:47
 * @Version 2.0
 */
public enum BillboardStatusEnum {

    // 状态：待揭榜1，攻关中2，已完成3
    WAIT(1, "待揭榜"),
    SOLVEING(2, "攻关中"),
    SOLVED(3, "已完成"),

    ;

    private final Integer code;
    private final String desc;

    private BillboardStatusEnum(Integer code, String desc) {
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
        for (BillboardStatusEnum targetEnum : BillboardStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}