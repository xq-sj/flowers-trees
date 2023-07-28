package com.sj.controller;

import cn.hutool.core.map.MapUtil;
import com.sj.config.flowerstrees.FlowersTreesConfig;
import com.sj.entity.User;
import com.sj.service.UserService;
import com.sj.utils.jwt.JwtUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {


    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private FlowersTreesConfig flowersTreesConfig;

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/test")
    @ApiOperation("测试")
    @PreAuthorize("hasAnyRole('user')")
    public ApiResult<?> test() {
        return ApiResult.success(jwtUtils.getJwtConfig());
    }

    @GetMapping("/test2")
    @ApiOperation("更新配置")
    public ApiResult<?> test2() {
        System.out.println("配置被修改");
        flowersTreesConfig.updateJwtConfig();
        return ApiResult.success();
    }

    @GetMapping("/test3")
    @ApiOperation("生成token")
    public ApiResult<?> getToken() {
        String token = jwtUtils.generateToken("花树-1");
        return ApiResult.success(ResultInfo.SUCCESS, token);
    }

    @GetMapping("/token/info")
    @ApiOperation("获取token内容")
    public ApiResult<?> getTokenContent(@RequestParam("token") String token) {
        Claims claims = jwtUtils.resolverToken(token);
        String subject = claims.getSubject();
        return ApiResult.success(ResultInfo.SUCCESS, subject);
    }

    @GetMapping("/check/token")
    @ApiOperation("检查token")
    public ApiResult<?> checkToken(HttpServletRequest request) {
        Claims claims = jwtUtils.checkToken(request);
        long tokenRemainingTime = jwtUtils.getTokenRemainingTime(claims);
        return ApiResult.success(MapUtil.builder().put("time", (tokenRemainingTime)).build());
    }

    @PutMapping("/register")
    @ApiOperation("注册")
    public ApiResult<?> register(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        boolean result = userService.save(user);
        if (result) {
            return ApiResult.success();
        }
        return ApiResult.error();
    }

}
