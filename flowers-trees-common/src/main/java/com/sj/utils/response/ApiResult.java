package com.sj.utils.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.xml.transform.Result;
import java.io.Serializable;

/**
 * @ClassName ApiResult
 * @Author 闪光灯
 * @Date 2023/7/26 20:18
 * @Description 返回结果数据格式封装
 */
@Data
@ToString
@ApiModel(value = "ApiResult对象", description = "返回结果数据格式封装")
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    private ApiResult() {

    }

    private ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功结果

    /**
     * 基础模板返回结果方法
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> success(Integer code, String message, T data) {
        return new ApiResult<T>(code, message, data);
    }

    /**
     * 枚举状态码和信息方法
     *
     * @param resultInfo 放回信息枚举类
     * @param data       数据
     * @param <T>        数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> success(ResultInfo resultInfo, T data) {
        return success(resultInfo.getCode(), resultInfo.getMessage(), data);
    }

    /**
     * 数据为空，指定状态码和结果信息方法
     *
     * @param code    状态码
     * @param message 信息
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> success(Integer code, String message) {
        return success(code, message, null);
    }

    /**
     * 数据为空，状态码默认200，指定返回信息方法
     *
     * @param message 返回信息
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> success(String message) {
        return success(ResultInfo.SUCCESS.getCode(), message);
    }

    /**
     * 状态码默认200，返回信息默认 “操作成功” 指定数据方法
     *
     * @param data 返回数据
     * @param <T>  返回类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> success(T data) {
        return success(ResultInfo.SUCCESS, data);
    }


    /**
     * 数据为空，状态码和返回信息由枚举对象指定
     *
     * @param resultInfo 枚举对象
     * @param <T>        数据类型
     * @return 返回结果
     */
    public static <T> ApiResult<T> success(ResultInfo resultInfo) {
        return success(resultInfo, null);
    }

    /**
     * 数据为空，状态码默认200返回信息默认“操作成功”
     *
     * @param <T> 数据类型
     * @return 返回结果
     */
    public static <T> ApiResult<T> success() {
        return success(ResultInfo.SUCCESS);
    }

    // ======================================================================================

    // 失败结果

    /**
     * 基础模板返回结果方法
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> error(Integer code, String message, T data) {
        return new ApiResult<T>(code, message, data);
    }

    /**
     * 枚举状态码和信息方法
     *
     * @param resultInfo 放回信息枚举类
     * @param data       数据
     * @param <T>        数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> error(ResultInfo resultInfo, T data) {
        return success(resultInfo.getCode(), resultInfo.getMessage(), data);
    }

    /**
     * 数据为空，指定状态码和结果信息方法
     *
     * @param code    状态码
     * @param message 信息
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> error(Integer code, String message) {
        return success(code, message, null);
    }

    /**
     * 数据为空，状态码默认200，指定返回信息方法
     *
     * @param message 返回信息
     * @param <T>     数据类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> error(String message) {
        return success(ResultInfo.ERROR.getCode(), message);
    }

    /**
     * 状态码默认200，返回信息默认 “操作成功” 指定数据方法
     *
     * @param data 返回数据
     * @param <T>  返回类型
     * @return 返回结果信息
     */
    public static <T> ApiResult<T> error(T data) {
        return success(ResultInfo.ERROR, data);
    }

    /**
     * 数据为空，状态码和返回信息由枚举对象指定
     *
     * @param resultInfo 枚举对象
     * @param <T>        数据类型
     * @return 返回结果
     */
    public static <T> ApiResult<T> error(ResultInfo resultInfo) {
        return success(resultInfo, null);
    }

    /**
     * 数据为空，状态码默认200返回信息默认“操作成功”
     *
     * @param <T> 数据类型
     * @return 返回结果
     */
    public static <T> ApiResult<T> error() {
        return success(ResultInfo.ERROR);
    }

}
