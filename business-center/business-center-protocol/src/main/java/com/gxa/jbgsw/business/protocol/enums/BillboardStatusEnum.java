package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/6/29 0029 17:47
 * @Version 2.0
 */
public enum BillboardStatusEnum {

    // 状态：0 待揭榜、1 已签约、2 解决中、3 已解决
    WAIT(0, "待揭榜"),
    SIGNED(1, "已签约"),
    SOLVEING(2, "解决中"),
    SOLVED(3, "已解决"),

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