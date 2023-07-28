package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/28 0028 14:14
 * @Version 2.0
 */
public enum MessageTypeEnum {
    // 0 系统消息  1 技术咨询
    SYS(0, "系统消息"),
    TEC(1, "技术咨询"),
    ;

    private final Integer code;
    private final String desc;

    private MessageTypeEnum(Integer code, String desc) {
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
        for (MessageTypeEnum targetEnum : MessageTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
