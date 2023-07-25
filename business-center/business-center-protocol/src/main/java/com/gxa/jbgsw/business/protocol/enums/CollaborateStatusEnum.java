package com.gxa.jbgsw.business.protocol.enums;

/**
 * Created by mac on 2023/7/24.
 */
public enum CollaborateStatusEnum {
    // 合作状态： 0 待沟通 1
    WAIT(0, "待沟通"),
    SUCCESS(1, "已沟通"),

    ;

    private final Integer code;
    private final String desc;

    private CollaborateStatusEnum(Integer code, String desc) {
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
        for (CollaborateStatusEnum targetEnum : CollaborateStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
