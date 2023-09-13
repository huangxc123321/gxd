package com.gxa.jbgsw.common.utils;

public class RedisKeys {
    /** 前缀 **/
    public static final String BASE_RSS = "gxa:";
    /** 用户信息 **/
    public static final String USER_INFO = BASE_RSS + "user:info:";
    /** 用户信息TOKEN **/
    public static final String USER_TOKEN = BASE_RSS + "user:token:";
    /** 二维码的UUID **/
    public static final String USER_UUID= BASE_RSS + "user:uuid:";
    /** 验证码 **/
    public static final String USER_VALIDATE_CODE = BASE_RSS + "user:validate:code:";
    /** 部门信息 **/
    public static final String DEPRTMENT_INFO = BASE_RSS + "deprtment:";

    /** 新闻发布时间 **/
    public static final String NEWS_PUBLIS_TIME = BASE_RSS + "news:publish:time:";

    /** 新增榜单生成对应的成果推荐，帅才推荐，经纪人推荐 的关联关系**/
    public static final String BILLBOARD_RELATED_RECOMMEND_TASK = BASE_RSS + "billboard:related:recommend:";

    /** 属性类型信息 **/
    public static final String DICTIONARY_TYPE_VALUE = BASE_RSS + "dictionary:type:";

    /** 任务推荐任务 **/
    public static final String HARVEST_RELATED_RECOMMEND_TASK = BASE_RSS + "harvest:related:recommend:";

    /** 新闻发布时间 **/
    public static final String TECH_DOMAIN_VALUE = BASE_RSS + "tech:domain:value:";
}
