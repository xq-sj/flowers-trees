package com.sj.config.flowerstrees.jwt;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName JwtConfig
 * @Author 闪光灯
 * @Date 2023/7/27 16:04
 * @Description Jwt配置类
 */
@Data
public class JwtConfig {

    /**
     * jwt密钥
     */
    private String secret;

    /**
     * jwt过期时间 单位秒(s)
     */
    private Integer expire;

    /**
     * jwt前缀
     */
    private String header;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * jwt过期前多久刷新 单位秒(s)
     */
    private Integer refresh;

}
