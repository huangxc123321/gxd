package com.gxa.jbgsw.basis.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/10 0010 12:16
 * @Version 2.0
 */
public enum BannerStatusEnum {


    // 状态: 0 生效  1 失效
    EFFECTIVE(0, "已生效"),
    LOSE_EFFECTIVE (1, "失效"),
    ;

    private final Integer code;
    private final String desc;

    private BannerStatusEnum(Integer code, String desc) {
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
        for (BannerStatusEnum targetEnum : BannerStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
