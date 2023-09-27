package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/8/10 0010 13:33
 * @Version 2.0
 */
public enum BillboardEconomicRelatedStatusEnum {


    // 状态： 0 待派单 1待接单  2 接受  3 不接受
    NOT_RECOMMEND(0, "待派单"),
    RECOMMEND(1, "待接单"),
    ACCEPT(2, "已接受"),
    NOT_ACCEPT(3, "已拒绝"),
    ;

    private final Integer code;
    private final String desc;

    private BillboardEconomicRelatedStatusEnum(Integer code, String desc) {
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
        for (BillboardEconomicRelatedStatusEnum targetEnum : BillboardEconomicRelatedStatusEnum.values()) {
            if (code.equals(targetEnum.code)) {
                return targetEnum.desc;
            }
        }
        return null;
    }


}
