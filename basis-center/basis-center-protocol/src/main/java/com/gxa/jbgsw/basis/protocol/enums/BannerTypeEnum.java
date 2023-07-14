package com.gxa.jbgsw.basis.protocol.enums;

public enum BannerTypeEnum {
    // 类型： 0 pc , 1 app
    PC(0, "pc"),
    APP (1, "app"),
    ;

    private final Integer code;
    private final String desc;

    private BannerTypeEnum(Integer code, String desc) {
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
        for (BannerTypeEnum targetEnum : BannerTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }


}
