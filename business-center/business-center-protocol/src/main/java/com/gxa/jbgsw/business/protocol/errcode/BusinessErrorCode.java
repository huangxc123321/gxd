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
    public static final ResultCode BUSINESS_PARAMS_DATE_ERROR = new ResultCode(3003, "有效期时间格式与规定的格式不一致");

   // public static final ResultCode BILLBOARD_TOP_MAX_ERROR = new ResultCode(3004, "最多只能允许4个置顶榜单，请重新选择或者取消之前设置的置顶");
    public static final ResultCode MOBILE_IS_EXIST = new ResultCode(3005, "手机号码已经注册，请换一个手机号经行注册");
    public static final ResultCode LEAVE_WORDS_IS_EXIST = new ResultCode(3006, "留言不能为空");
    public static final ResultCode BUZ_IS_NOT_EXIST = new ResultCode(3007, "企业不存在");

    public static final ResultCode BILLBOARD_IS_NOT_EXIST = new ResultCode(3008, "榜单不存在");
    public static final ResultCode SELF_PUBLISH_SELF_NOT_GAIN = new ResultCode(3009, "自己发布的榜单，自己不能揭榜");

    public static final ResultCode EXISTS_COLLABORATE = new ResultCode(3010, "已经发起过合作，不能再次发起合作");

    public static final ResultCode GOV_BILLBOARD_TOP_MAX_ERROR = new ResultCode(3011, "最多只能允许9个置顶榜单，请重新选择或者取消之前设置的置顶");
    public static final ResultCode BUZ_BILLBOARD_TOP_MAX_ERROR = new ResultCode(3012, "最多只能允许5个置顶榜单，请重新选择或者取消之前设置的置顶");


}
