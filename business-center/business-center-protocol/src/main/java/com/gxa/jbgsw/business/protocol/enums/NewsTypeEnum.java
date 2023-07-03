package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/3 0003 14:57
 * @Version 2.0
 */
public enum NewsTypeEnum {

    // 新闻/政策类型： 0 新闻资讯 1 公司公告 2 政策动态
    NEWS_INFO(0, "新闻资讯"),
    CO_ANNOUN(1, "公司公告"),
    POLICY_DYNAMICS (2, "政策动态"),
    ;

    private final Integer code;
    private final String desc;

    private NewsTypeEnum(Integer code, String desc) {
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
        for (NewsTypeEnum targetEnum : NewsTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
