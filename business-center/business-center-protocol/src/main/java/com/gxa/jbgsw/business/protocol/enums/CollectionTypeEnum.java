package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/26 0026 9:00
 * @Version 2.0
 */
public enum CollectionTypeEnum {
    // 收藏类型： 0 政府榜 1 企业榜 2 成果 3 政策 4 帅才
    GOV(0, "政府榜"),
    BUZ(1, "企业榜"),
    HAVEST(2, "成果"),
    poc(3, "政策"),
    talent(4, "帅才"),

    ;

    private final Integer code;
    private final String desc;

    private CollectionTypeEnum(Integer code, String desc) {
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
        for (CollectionTypeEnum targetEnum : CollectionTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
