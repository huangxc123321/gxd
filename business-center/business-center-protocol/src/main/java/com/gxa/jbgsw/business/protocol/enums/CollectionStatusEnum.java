package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/26 0026 8:38
 * @Version 2.0
 */
public enum CollectionStatusEnum {
    // 收藏状态： 0 收藏 1 取消收藏
    COLLECTION(0, "收藏"),
    CANCEL(1, "取消收藏"),

    ;

    private final Integer code;
    private final String desc;

    private CollectionStatusEnum(Integer code, String desc) {
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
        for (CollectionStatusEnum targetEnum : CollectionStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
