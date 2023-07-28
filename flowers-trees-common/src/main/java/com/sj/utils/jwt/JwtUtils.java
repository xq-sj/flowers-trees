package com.sj.utils.jwt;

import cn.hutool.core.util.StrUtil;
import com.sj.config.flowerstrees.jwt.JwtConfig;
import com.sj.constant.CommonConst;
import com.sj.exception.CustomException;
import com.sj.utils.response.ResultInfo;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName JwtUtils
 * @Author 闪光灯
 * @Date 2023/7/27 15:50
 * @Description 691200
 */
@Data
@Component
@Slf4j
public class JwtUtils {

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 生成token
     *
     * @param sub 接收用户
     * @return token值
     */
    public String generateToken(String sub) {
        return jwtConfig.getPrefix() + CommonConst.SPACE_SYMBOL + Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject(sub)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    /**
     * 检查token
     *
     * @param request 请求
     * @return 解析token对象
     */
    public Claims checkToken(HttpServletRequest request) {
        // 获取token
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        // 没有token
        if (StrUtil.isEmpty(bearerToken)) {
            throw new CustomException(ResultInfo.NO_LOGIN);
        }
        // token前缀错误
        if (!bearerToken.startsWith(jwtConfig.getPrefix())) {
            throw new CustomException(ResultInfo.TOKEN_ERROR);
        }
        String[] bearerTokenSplit = bearerToken.split(CommonConst.SPACE_SYMBOL);
        if(bearerTokenSplit.length != 2){
            throw new CustomException(ResultInfo.TOKEN_ERROR);
        }
        // 返回解析token对象
        return resolverToken(bearerTokenSplit[1]);
    }

    /**
     * 解析token
     *
     * @param token 需要解析的token字符串 不带前缀
     * @return 解析token对象
     */
    public Claims resolverToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("token已过期");
            throw new CustomException(ResultInfo.TOKEN_OVERDUE);
        } catch (JwtException e) {
            log.error("错误的token");
            throw new CustomException(ResultInfo.TOKEN_ERROR);
        }
    }

    /**
     * 获取token剩余时间 单位秒(s)
     *
     * @param claims 解析的token对象
     * @return 剩余时间 单位秒(s)
     */
    public long getTokenRemainingTime(Claims claims) {
        if (claims == null) {
            return 0;
        }
        return (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
    }

}
