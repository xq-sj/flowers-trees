package com.sj.config.flowerstrees.third;

import lombok.Data;

/**
 * @ClassName AuthConfig
 * @Author 闪光灯
 * @Date 2023/8/1 17:42
 * @Description 第三方相关配置
 */
@Data
public class ThirdPartyConfig {

    /**
     * 短信验证码注册键
     */
    private String smsRegisterKey;

    /**
     * 短信验证码登录键
     */
    private String smsLoginKey;

    /**
     * 短信验证码忘记密码键
     */
    private String smsForgetKey;

    /**
     * 短信验证码过期时间
     */
    private Integer smsExpire;

    /**
     * 短信验证码注册信息模板
     */
    private String smsRegisterContent;

    /**
     * 短信验证码登录信息模板
     */
    private String smsLoginContent;


    /**
     * 短信验证码忘记密码信息模板
     */
    private String smsForgetContent;

    /**
     * 短信验证码变量
     */
    private String smsCodeVar;

    /**
     * 短信时间变量
     */
    private String smsTimeVar;

    /**
     * 短信验证码最小值
     */
    private Integer minCode;

    /**
     * 短信验证码最大值
     */
    private Integer maxCode;

    /**
     * 短信验证码获取次数键
     */
    private String smsSendCountKey;

    /**
     * 短信验证码规定获取次数
     */
    private Integer smsCount;

    /**
     * 短信验证码规定获取次数刷新时间
     */
    private Integer smsCountRefresh;

    /**
     * 短信验证码获取多次提示信息
     */
    private String smsOverCountMsg;

    /**
     * 短信验证码获取次数变量
     */
    private String smsCountVar;

    /**
     * 短信验证码刷新时间变量
     */
    private String smsCountRefreshTimeVar;

    /**
     * 短信验证码刷新时间单位变量
     */
    private String smsCountRefreshUnitVar;

    /**
     * 短信验证码刷新时间单位
     */
    private String smsCountRefreshUnit;

    /**
     * 短信验证码获取次数刷新时间
     */
    private Integer smsCountRefreshTime;
}
