package com.sj.exception;

import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalException
 * @Author 闪光灯
 * @Date 2023/7/27 19:11
 * @Description 全局异常捕获
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> handler(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError == null) {
            log.error("请求参数异常，{}", e.getMessage());
            return ApiResult.error(e.getMessage());
        }
        log.error("请求参数异常，{}", fieldError.getDefaultMessage());
        return ApiResult.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(value = CustomException.class)
    public ApiResult<?> handler(CustomException e) {
        log.error("自定义异常，{}", e.getMessage());
        if (e.getResultInfo() != null) {
            return ApiResult.error(e.getResultInfo());
        }
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ApiResult<?> handler(IllegalArgumentException e) {
        log.error("参数异常，{}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(value = MultipartException.class)
    public ApiResult<?> handler(MaxUploadSizeExceededException e) {
        log.error("文件上传异常，{}", e.getMessage());
        return ApiResult.error("文件过大，最大上传2MB文件");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ApiResult<?> handler(AccessDeniedException e) {
        log.error("权限不足，{}", e.getMessage());
        return ApiResult.error(ResultInfo.FORBIDDEN);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult<?> handlerNotFoundException(NoHandlerFoundException e) {
        log.error("资源未找到，{}", e.getMessage());
        return ApiResult.error(ResultInfo.NOT_FONT);
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ApiResult<?> handler(SQLIntegrityConstraintViolationException e) {
        log.error("sql异常，{}", e.getMessage());
        return ApiResult.error(ResultInfo.ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ApiResult<?> handler(RuntimeException e) {
        log.error("运行时异常，{}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

}
