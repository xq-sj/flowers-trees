package com.sj.constant;

/**
 * @ClassName ConfigConst
 * @Author 闪光灯
 * @Date 2023/7/27 17:17
 * @Description 配置项的常量类
 */
public class ConfigConst {

    /**
     * 花树博客配置项
     */
    public final static String FLOWERS_TREES = "flowers_trees";


    // ======================用户信息相关常量开始=====================
    /**
     * 用户相关配置编码
     */
    public final static String USER_INFO_CODE = "user_info";

    /**
     * 权限缓存key
     */
    public final static String AUTH_CACHE_KEY = "auth_cache_key";

    /**
     * 登录用户key
     */
    public final static String LOGIN_USER_KEY = "login_user_key";
    // ======================用户信息相关常量结束=====================


    // ===========================jwt相关常量开始===================
    /**
     * jwt配置编码
     */
    public final static String JWT_CONFIG_CODE = "jwt_config";

    /**
     * 密钥编码
     */
    public final static String JWT_CONFIG_SECRET = "jwt_secret";

    /**
     * 过期时间编码
     */
    public final static String JWT_CONFIG_EXPIRE = "jwt_expire";

    /**
     * 请求头编码
     */
    public final static String JWT_CONFIG_HEADER = "jwt_header";

    /**
     * 前缀
     */
    public final static String JWT_CONFIG_PREFIX = "jwt_prefix";

    /**
     * jwt过期前多久刷新 单位秒(s)
     */
    public final static String JWT_CONFIG_REFRESH = "jwt_refresh";

    // ===========================jwt相关常量结束===================

}
