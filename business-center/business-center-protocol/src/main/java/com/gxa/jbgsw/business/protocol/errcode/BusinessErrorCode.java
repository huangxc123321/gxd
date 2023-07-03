package com.gxa.jbgsw.business.protocol.errcode;

import com.gxa.jbgsw.common.utils.ResultCode;

/**
 * @Author Mr. huang
 * @Date 2023/6/28 0028 15:05
 * @Version 2.0
 */
public class BusinessErrorCode {

    public static final ResultCode BUSINESS_PARAMS_ERROR = new ResultCode(3001, "参数不正确");
    public static final ResultCode GOV_BILLBOARD_PUBLISH_ERROR = new ResultCode(3002, "政府榜单只能由政府部门来发布");

}
