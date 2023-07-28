package com.sj.config.handler;

import cn.hutool.json.JSONUtil;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName LoginFailHandler
 * @Author 闪光灯
 * @Date 2023/7/27 22:37
 * @Description 登录失败处理器
 */
public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        // 如果异常是错误账户密码 自己处理异常信息
        if (exception instanceof UsernameNotFoundException) {
            outputStream.write(JSONUtil.toJsonStr(ApiResult.error(ResultInfo.USER_NO_REGISTER)).getBytes(StandardCharsets.UTF_8));
        } else if (exception instanceof BadCredentialsException) {
            outputStream.write(JSONUtil.toJsonStr(ApiResult.error(ResultInfo.PASSWORD_ERROR)).getBytes(StandardCharsets.UTF_8));
        } else {
            // 如果是其他错误信息，比如验证码错误，直接打印 捕获的异常信息
            exception.printStackTrace();
            outputStream.write(JSONUtil.toJsonStr(ApiResult.error()).getBytes(StandardCharsets.UTF_8));
        }
        outputStream.flush();
        outputStream.close();
    }

}
