package com.gxa.jbgsw.business.protocol.enums;


public enum AttentionTypeEnum {


    // 关注类型： 0 政府 1 企业 2 帅才
    GOV(0, "政府"),
    BUZ(1, "企业"),
    TALENT(2, "帅才"),

    ;

    private final Integer code;
    private final String desc;

    private AttentionTypeEnum(Integer code, String desc) {
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
        for (AttentionTypeEnum targetEnum : AttentionTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
