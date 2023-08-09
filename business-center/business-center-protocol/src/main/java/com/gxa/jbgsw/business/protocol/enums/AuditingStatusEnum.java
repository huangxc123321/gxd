package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/7 0007 10:40
 * @Version 2.0
 */
public enum AuditingStatusEnum {

    // 揭榜审核状态：0 待审核  1 审核通过 2 审核未通过
    WAIT(0, "待审核"),
    PASS(1, "审核通过"),
    NO_PASS(2, "审核未通过"),

    ;

    private final Integer code;
    private final String desc;

    private AuditingStatusEnum(Integer code, String desc) {
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
        for (AuditingStatusEnum targetEnum : AuditingStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }
}
