package com.sj.config.handler;

import com.sj.utils.mine.MineUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationEntryPoint
 * @Author 闪光灯
 * @Date 2023/7/28 3:00
 * @Description 认证异常
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private MineUtils mineUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        mineUtils.responseMessage(response, ApiResult.error(ResultInfo.NO_LOGIN));
    }
}
