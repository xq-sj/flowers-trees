package com.sj.config.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.sj.config.flowerstrees.jwt.JwtConfig;
import com.sj.config.flowerstrees.user.LoginUser;
import com.sj.config.flowerstrees.user.UserConfig;
import com.sj.config.security.SecurityUser;
import com.sj.constant.CommonConst;
import com.sj.entity.User;
import com.sj.exception.CustomException;
import com.sj.service.UserService;
import com.sj.utils.jwt.JwtUtils;
import com.sj.utils.mine.MineUtils;
import com.sj.utils.redis.RedisUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @ClassName LoginSuccessHandler
 * @Author 闪光灯
 * @Date 2023/7/27 20:18
 * @Description 登录成功处理器
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserService userService;

    @Resource
    private MineUtils mineUtils;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserConfig userConfig;

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        SecurityUser securityUser = null;
        String authorities = "";
        System.out.println(authentication);
        if (authentication.getPrincipal() instanceof SecurityUser) {
            // 获取登录用户信息
            securityUser = (SecurityUser) authentication.getPrincipal();
            // 获取登录用户权限信息
            if (authentication.getAuthorities().size() > 0) {
                authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
            }
        }
        if (securityUser == null) {
            // 用户信息异常
            throw new CustomException(ResultInfo.USER_INFO_ERROR);
        }
        // 找到用户说明登录成功
        // 生成token，查询用户详细信息，抹除密码返回结果
        // token生成包含信息 用户id-用户名
        String token = jwtUtils.generateToken(securityUser.getId() + CommonConst.SHORT_TRANSVERSE_LINE + securityUser.getUsername());
        User user = userService.getById(securityUser.getId());
        user.setLastLogin(LocalDateTime.now());
        user.setLastIp(mineUtils.getIpAddress(request));
        userService.updateById(user);
        user.setPassword(null);
        LoginUser loginUser = LoginUser.builder()
                .authority(authorities)
                .token(token)
                .user(user)
                .build();
        String loginUserJson = JSONUtil.toJsonStr(loginUser);
        // 将当前用户存入redis
        redisUtils.hset(userConfig.getLoginUserKey(), user.getUsername(), loginUserJson, jwtConfig.getExpire());
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUser(), null, AuthorityUtils.commaSeparatedStringToAuthorityList(loginUser.getAuthority()));
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        ApiResult<LoginUser> loginResult = ApiResult.success(ResultInfo.LOGIN_SUCCESS, loginUser);
        // 将结果返回给前端
        mineUtils.responseMessage(response, loginResult);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

}
