package com.gxa.jbgsw.user.protocol.errcode;


import com.gxa.jbgsw.common.utils.ResultCode;

public class UserErrorCode {
    // 1000~1999
    public static final ResultCode USER_CODE_IS_EXISTS = new ResultCode(1001, "该账号已注册");
    public static final ResultCode USER_PHONE_IS_EXISTS = new ResultCode(1002, "该手机已注册");
    public static final ResultCode USER_PHONE_IS_ERROR = new ResultCode(1003, "输入的手机号不正确");

    public static final ResultCode LOGIN_CODE_ERROR = new ResultCode(1004, "用户名或密码错误");
    public static final ResultCode LOGIN_PASSWORD_ERROR = new ResultCode(1005, "输入的密码错误");

    public static final ResultCode LOGIN_VALIDATECODE_IS_NULL = new ResultCode(1006, "验证码不能为空");
    public static final ResultCode LOGIN_VALIDATECODE_IS_ERROR = new ResultCode(1007, "验证码不对");
    public static final ResultCode LOGIN_SESSION_EXPIRE = new ResultCode(1008, "账号未登录或者登录已过期");
    public static final ResultCode LOGIN_ORIGINAL_PASSWORD_IS_NULL = new ResultCode(1009, "初始密码没有设置");
    public static final ResultCode LOGIN_OLD_PASSWORD_ERROR = new ResultCode(1010, "输入的旧密码错误");
    public static final ResultCode ROLE_IS_EXIST = new ResultCode(1011, "角色已存在");
    public static final ResultCode TOKEN_NOT_EXIST = new ResultCode(1012, "token 不存在");
    public static final ResultCode USER_PARAMS_ERROR = new ResultCode(1013, "参数不正确");

    public static final ResultCode QR_CODE_EXPIRE = new ResultCode(1014, "二维码已失效");
    public static final ResultCode QR_CODE_2 = new ResultCode(1015, "二维码已确认");

    public static final ResultCode CHATGPT_MESSAGE_IS_NULL = new ResultCode(1016, "消息不能为空");
    public static final ResultCode USER_IS_NOT_ECONOMICMAN = new ResultCode(1017, "该用户不是经纪人");

    public static final ResultCode USER_IS_NOT_ALLOW_LOGIN = new ResultCode(1018, "无权限");

}
