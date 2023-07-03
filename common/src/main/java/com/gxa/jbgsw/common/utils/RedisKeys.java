package com.gxa.jbgsw.common.utils;

public class RedisKeys {
    /** 前缀 **/
    public static final String BASE_RSS = "gxa:";
    /** 用户信息 **/
    public static final String USER_INFO = BASE_RSS + "user:info:";
    /** 用户信息TOKEN **/
    public static final String USER_TOKEN = BASE_RSS + "user:token:";
    /** 验证码 **/
    public static final String USER_VALIDATE_CODE = BASE_RSS + "user:validate:code:";
    /** 部门信息 **/
    public static final String DEPRTMENT_INFO = BASE_RSS + "deprtment:";

    /** 新闻发布时间 **/
    public static final String NEWS_PUBLIS_TIME = BASE_RSS + "news:publish:time:";

    /** 属性类型信息 **/
    public static final String DICTIONARY_TYPE_VALUE = BASE_RSS + "dictionary:type:";
}
