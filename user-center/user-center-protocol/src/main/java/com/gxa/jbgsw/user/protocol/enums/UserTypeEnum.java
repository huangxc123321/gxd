package com.gxa.jbgsw.user.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/8/18 0018 13:20
 * @Version 2.0
 */
public enum UserTypeEnum {

    // 用户类型：
    GOV(1, "政府部门"),
    BUZ(2, "企业"),
    TEAM(3, "科研机构和团队"),
    EDU(4, "大学院校"),
    PERSON(5, "个人"),
    ;

    private final Integer code;
    private final String desc;

    private UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getNameByIndex(Integer code) {
        for (UserTypeEnum targetEnum : UserTypeEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }

}
