package com.sj.config.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sj.config.flowerstrees.jwt.JwtConfig;
import com.sj.config.flowerstrees.user.LoginUser;
import com.sj.config.flowerstrees.user.UserConfig;
import com.sj.constant.CommonConst;
import com.sj.exception.CustomException;
import com.sj.utils.jwt.JwtUtils;
import com.sj.utils.mine.MineUtils;
import com.sj.utils.redis.RedisUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoqi
 * Date: 2022-03-11
 * Time: 上午 8:50
 * Description: Jwt过滤器
 *
 * @author xiaoqi
 * @since 2022-03-11
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserConfig userConfig;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private MineUtils mineUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 如果没有携带token 直接放行
        String authorization = request.getHeader(jwtConfig.getHeader());
        if (StrUtil.isEmpty(authorization)) {
            chain.doFilter(request, response);
            return;
        }
        Claims claims = null;
        try {
            // 检查token是否正确
            claims = jwtUtils.checkToken(request);
        } catch (CustomException e) {
            mineUtils.responseMessage(response, ApiResult.error(e.getResultInfo()));
            return;
        }
        // 获取token中包含的用户信息 用户id-用户名
        String userInfo = claims.getSubject();
        String[] userInfoSplit = userInfo.split(CommonConst.SHORT_TRANSVERSE_LINE);
        String username = userInfoSplit[1];

        // 判断当前用户是否在在线用户中
        if (curUserIsNotLogin(response, username)) {
            return;
        }
        // 判断当前用户是否被挤下线
        LoginUser loginUser = curUserIsOffLine(request, response, username);
        if (loginUser == null) {
            return;
        }
        // 判断当前用户是否被冻结
        if (curUserIsBlocked(response, loginUser)) {
            return;
        }
        // 认证通过，设置当前用户的认证信息
        setAuthenticationToken(request, loginUser);
        // 刷新token
        refreshToken(loginUser, claims, response);
        // 放行
        chain.doFilter(request, response);
    }

    /**
     * 检查当前token剩余过期时间，token过期时间为7天，如果当前用户在过期前的两天内携带token访问过接口就自动刷新token，将新的token存放在response中携带给前端
     *
     * @param loginUser 当前用户信息
     * @param claims    token解析对象
     * @param response  返回结果对象
     */
    private void refreshToken(LoginUser loginUser, Claims claims, HttpServletResponse response) {
        long tokenRemainingTime = jwtUtils.getTokenRemainingTime(claims);
        // 如果剩余时间小于token刷新时间范围，就重新生成token存放在response对象中返回，并且更新到缓存中
        if (tokenRemainingTime < jwtConfig.getRefresh()) {
            log.info(loginUser.getUser().getUsername() + " -> token刷新...");
            String newToken = jwtUtils.generateToken(loginUser.getUser().getId() + CommonConst.SHORT_TRANSVERSE_LINE + loginUser.getUser().getUsername());
            loginUser.setToken(newToken);
            String loginUserJson = JSONUtil.toJsonStr(loginUser);
            // 将新的用户信息存放在缓存中
            redisUtils.hset(userConfig.getLoginUserKey(), loginUser.getUser().getUsername(), loginUserJson, jwtConfig.getExpire());
            response.setHeader(jwtConfig.getHeader(), newToken);
            log.info(loginUser.getUser().getUsername() + " -> token更新完成...");
        }
    }

    /**
     * 设置当前用户的认证信息
     *
     * @param request   请求对象
     * @param loginUser 当前用户信息
     */
    private static void setAuthenticationToken(HttpServletRequest request, LoginUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUser(), null, AuthorityUtils.commaSeparatedStringToAuthorityList(loginUser.getAuthority()));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * 判断当前用户是否被冻结
     *
     * @param response  返回结果对象
     * @param loginUser 当前用户信息
     * @return 是否被冻结，被冻结返回true
     */
    private boolean curUserIsBlocked(HttpServletResponse response, LoginUser loginUser) {
        // 如果一致检查当前用户是否被禁用
        if (!loginUser.getUser().getStatus().equals(CommonConst.ACTIVE)) {
            mineUtils.responseMessage(response, ApiResult.error(ResultInfo.FORBIDDEN_USER));
            return true;
        }
        return false;
    }

    /**
     * 判断当前用户是否被挤下线
     *
     * @param request  请求对象
     * @param response 返回结果对象
     * @param username 用户名
     * @return 是否被挤下线 被挤下线返回true
     */
    private LoginUser curUserIsOffLine(HttpServletRequest request, HttpServletResponse response, String username) {
        // 如果在，检查缓存中的token和当前token是否一致，不一致则说明账号被挤下线
        String loginUserJson = redisUtils.hget(userConfig.getLoginUserKey(), username).toString();
        LoginUser loginUser = JSONUtil.toBean(loginUserJson, LoginUser.class);
        // 从请求头中获取带Bearer前缀的token
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (!StrUtil.equals(loginUser.getToken(), bearerToken)) {
            mineUtils.responseMessage(response, ApiResult.error(ResultInfo.REMOTE_LOGIN));
            return null;
        }
        return loginUser;
    }

    /**
     * 判断当前用户是否在在线用户中
     *
     * @param response 返回结果对象
     * @param username 用户名
     * @return 没有登录标识，没有登录返回true
     */
    private boolean curUserIsNotLogin(HttpServletResponse response, String username) {
        boolean flag = redisUtils.hHasKey(userConfig.getLoginUserKey(), username);
        // 如果当前用户不在在线用户中，提示未登录
        if (!flag) {
            mineUtils.responseMessage(response, ApiResult.error(ResultInfo.NO_LOGIN));
            return true;
        }
        return false;
    }
}