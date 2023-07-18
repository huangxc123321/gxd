package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/18 0018 10:50
 * @Version 2.0
 */
public enum ShareCommunityTypeEnum {

    // 分享类型：0 文章  1 视频
    article(0, "文章"),
    video(1, "视频"),
    ;

    private final Integer code;
    private final String desc;

    private ShareCommunityTypeEnum(Integer code, String desc) {
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
        for (ShareCommunityTypeEnum targetEnum : ShareCommunityTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
