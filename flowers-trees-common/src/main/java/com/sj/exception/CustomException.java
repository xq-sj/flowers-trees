package com.sj.exception;

import com.sj.utils.response.ResultInfo;
import lombok.Data;

/**
 * @ClassName CustomException
 * @Author 闪光灯
 * @Date 2023/7/27 19:06
 * @Description 自定义异常
 */
@Data
public class CustomException extends RuntimeException {

    private ResultInfo resultInfo;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(ResultInfo msg) {
        super(msg.getMessage());
        resultInfo = msg;
    }
}