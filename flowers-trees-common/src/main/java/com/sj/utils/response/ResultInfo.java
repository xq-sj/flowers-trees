package com.sj.utils.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ResultInfo
 * @Author 闪光灯
 * @Date 2023/7/26 20:20
 * @Description 返回结果状态码和信息枚举封装
 */
@AllArgsConstructor
@Getter
public enum ResultInfo {

    /**
     * 成功结果
     */
    // 操作成功
    SUCCESS(200, "操作成功"),
    // 登录成功
    LOGIN_SUCCESS(200, "登录成功"),

    /**
     * 失败结果
     */
    // 操作失败
    ERROR(400, "操作失败"),
    // 没有登录
    NO_LOGIN(401, "您还未登录，请先登录"),
    // 权限不足
    FORBIDDEN(403, "权限不足"),
    // 资源未找到
    NOT_FONT(404, "请求资源未找到"),
    // token过期
    TOKEN_OVERDUE(412, "您的认证已过期，请重新登录"),
    // token令牌错误
    TOKEN_ERROR(413, "认证错误，错误的令牌"),
    // 用户信息异常
    USER_INFO_ERROR(415, "用户信息异常"),
    // 资源已存在
    ALREADY_EXIST(417, "请勿重复添加"),
    // 用户未注册
    USER_NO_REGISTER(418, "未找到该用户，快去注册一个吧！"),
    // 用户未注册
    PASSWORD_ERROR(419, "密码错误，再想想呢~"),
    // 账号异地登录被强制下线
    REMOTE_LOGIN(420, "您的账号在异地登录，请重新认证~"),
    // 账号禁用
    FORBIDDEN_USER(421, "您的账号已被禁用，反省一下找管理员解释吧~"),

    // 服务器未知错误
    SERVER_ERROR(500, "服务器异常，请联系管理员"),

    ;

    private Integer code;

    private String message;


}
