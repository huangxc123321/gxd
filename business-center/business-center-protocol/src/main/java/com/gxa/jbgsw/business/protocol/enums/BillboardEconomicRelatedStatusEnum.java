package com.gxa.jbgsw.business.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/8/10 0010 13:33
 * @Version 2.0
 */
public enum BillboardEconomicRelatedStatusEnum {


    // 状态： 0 不推荐 1推荐(派单)  2 接受  3 不接受
    NOT_RECOMMEND(0, "不推荐"),
    RECOMMEND(1, "推荐"),
    ACCEPT(2, "接受"),
    NOT_ACCEPT(3, "不接受"),
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
