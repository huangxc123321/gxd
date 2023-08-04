package com.gxa.jbgsw.basis.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/8/4 0004 16:18
 * @Version 2.0
 */
public enum LeaveWordsReplayEnum {

    // 是否回复： 0 待回复 ，1 已回复
    WAIT(0, "待回复"),
    SUCESS (1, "已回复"),
    ;

    private final Integer code;
    private final String desc;

    private LeaveWordsReplayEnum(Integer code, String desc) {
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
        for (LeaveWordsReplayEnum targetEnum : LeaveWordsReplayEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
