package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 10:52
 * @Version 2.0
 */
public enum ShareCommunityStatusEnum {

    // 状态：  0 待审核  1 已审核  2 未通过
    WAIT(0, "待审核"),
    AUDITED(1, "已审核"),
    NOT_PASS(2, "未通过"),

    ;

    private final Integer code;
    private final String desc;

    private ShareCommunityStatusEnum(Integer code, String desc) {
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
        for (ShareCommunityStatusEnum targetEnum : ShareCommunityStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
