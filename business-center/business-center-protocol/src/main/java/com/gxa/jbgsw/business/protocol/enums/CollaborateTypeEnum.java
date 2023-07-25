package com.gxa.jbgsw.business.protocol.enums;

/**
 * Created by mac on 2023/7/24.
 */
public enum CollaborateTypeEnum {
    // 合作类型：0 成果合作  1 需求合作
    GAIN(0, "成果合作"),
    REQUIREMENT(1, "需求合作"),

    ;

    private final Integer code;
    private final String desc;

    private CollaborateTypeEnum(Integer code, String desc) {
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
        for (CollaborateTypeEnum targetEnum : CollaborateTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
