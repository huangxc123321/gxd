package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/26 0026 9:23
 * @Version 2.0
 */
public enum AttentionStatusEnum {
    // 关注状态： 0 关注 1 取消关注
    ATTENTION(0, "关注"),
    CANCEL(1, "取消关注"),

    ;

    private final Integer code;
    private final String desc;

    private AttentionStatusEnum(Integer code, String desc) {
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
        for (AttentionStatusEnum targetEnum : AttentionStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
