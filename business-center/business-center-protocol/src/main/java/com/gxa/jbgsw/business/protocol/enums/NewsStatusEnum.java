package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 13:44
 * @Version 2.0
 */
public enum NewsStatusEnum {

    // 状态：0 发布， 1 待发布
    PUBLISHED(0, "发布"),
    WAIT(1, "待发布"),
    ;

    private final Integer code;
    private final String desc;

    private NewsStatusEnum(Integer code, String desc) {
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
        for (NewsStatusEnum targetEnum : NewsStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
