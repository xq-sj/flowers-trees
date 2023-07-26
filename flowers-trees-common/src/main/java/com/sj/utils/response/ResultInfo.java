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
    // 资源未找到
    NOT_FONT(404, "请求资源未找到"),
    // token失效 或是 假冒token
    TOKEN_ERROR(413, "您的登录信息已过期，请重新登录"),
    // 没有登录
    NO_LOGIN(401, "您还未登录，请先登录"),
    // 资源已存在
    ALREADY_EXIST(417,"请勿重复添加"),
    // 服务器未知错误
    SERVER_ERROR(500, "服务器异常，请联系管理员");

    private Integer code;

    private String message;


}
