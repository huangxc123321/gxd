package com.gxa.jbgsw.user.protocol.enums;

/**
 * @Author Mr. huang
 * @Date 2023/7/31 0031 15:52
 * @Version 2.0
 */
public enum QrCodeStatusEnum {

    WAITING(1,"待扫描"),
    SCANNED(2,"已确认"),
    CONFIRMED(3,"已登录"),
    INVALID(4,"二维码无效"),
    CANCEL(5,"已取消");

    private Integer statusCode;
    private String statusValue;

    QrCodeStatusEnum(Integer statusCode, String statusValue) {
        this.statusCode = statusCode;
        this.statusValue = statusValue;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusValue() {
        return statusValue;
    }

}
