package com.sj.config.handler;

import com.sj.utils.mine.MineUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAccessDeniedHandler
 * @Author 闪光灯
 * @Date 2023/7/28 14:20
 * @Description 权限不足异常处理器
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private MineUtils mineUtils;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        mineUtils.responseMessage(response, ApiResult.error(ResultInfo.FORBIDDEN));
    }
}
