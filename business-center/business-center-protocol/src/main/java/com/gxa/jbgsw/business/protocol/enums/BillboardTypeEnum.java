package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/6 0006 13:44
 * @Version 2.0
 */
public enum BillboardTypeEnum {

    // 榜单类型： 0 政府榜 1 企业榜
    GOV_BILLBOARD(0, "政府榜"),
    BUS_BILLBOARD(1, "企业榜"),

    ;

    private final Integer code;
    private final String desc;

    private BillboardTypeEnum(Integer code, String desc) {
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
        for (BillboardTypeEnum targetEnum : BillboardTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
