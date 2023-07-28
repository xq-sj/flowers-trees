package com.sj.config.flowerstrees.user;

import lombok.Data;

/**
 * @ClassName UserConfig
 * @Author 闪光灯
 * @Date 2023/7/27 21:18
 * @Description 用户相关配置
 */
@Data
public class UserConfig {

    /**
     * 用户权限缓存key
     */
    private String authCacheKey;

    /**
     * 登录用户key
     */
    private String loginUserKey;

}
