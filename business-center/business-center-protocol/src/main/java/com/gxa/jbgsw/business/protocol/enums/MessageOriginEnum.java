package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/28 0028 14:08
 * @Version 2.0
 */
public enum MessageOriginEnum {

    // 系统消息来源：0 揭榜申请 1 榜单推荐 2 合作发起
    APPLY(0, "揭榜申请"),
    RECOMMEND(1, "榜单推荐"),
    COLLABORATE(2, "合作发起"),
    ;

    private final Integer code;
    private final String desc;

    private MessageOriginEnum(Integer code, String desc) {
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
        for (MessageOriginEnum targetEnum : MessageOriginEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
