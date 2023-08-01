package com.sj.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sj.config.flowerstrees.FlowersTreesConfig;
import com.sj.config.flowerstrees.jwt.JwtConfig;
import com.sj.config.flowerstrees.third.ThirdPartyConfig;
import com.sj.config.flowerstrees.user.LoginUser;
import com.sj.config.flowerstrees.user.UserConfig;
import com.sj.constant.CommonConst;
import com.sj.constant.ConfigConst;
import com.sj.entity.User;
import com.sj.entity.UserRole;
import com.sj.model.vo.RegisterVo;
import com.sj.service.UserRoleService;
import com.sj.service.UserService;
import com.sj.utils.jwt.JwtUtils;
import com.sj.utils.mine.MineUtils;
import com.sj.utils.redis.RedisUtils;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

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
@Slf4j
public class UserController {


    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ThirdPartyConfig thirdPartyConfig;

    @Resource
    private UserConfig userConfig;

    @Resource
    private MineUtils mineUtils;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private JwtConfig jwtConfig;

    @GetMapping("/code")
    @ApiOperation("获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true),
            @ApiImplicitParam(name = "type", value = "验证码类型：[0：注册，1：登录，2：忘记密码]", required = true)
    })
    public ApiResult<?> getCode(@RequestParam(value = "phone", required = true) String phone,
                                @RequestParam(value = "type", required = true) Integer type) {
        // 判断当前用户在规定时间内获取的验证码次数是否超标
        boolean flag = redisUtils.hHasKey(thirdPartyConfig.getSmsSendCountKey(), phone);
        if (flag) {
            int sendCount = Integer.parseInt(redisUtils.hget(thirdPartyConfig.getSmsSendCountKey(), phone).toString());
            Integer smsCount = thirdPartyConfig.getSmsCount();
            if (sendCount >= smsCount) {
                // 刷新时间变量
                String smsCountRefreshTimeVar = thirdPartyConfig.getSmsCountRefreshTimeVar();
                // 刷新时间值
                Integer smsCountRefreshTime = thirdPartyConfig.getSmsCountRefreshTime();
                // 刷新时间单位变量
                String smsCountRefreshUnitVar = thirdPartyConfig.getSmsCountRefreshUnitVar();
                // 刷新时间单位值
                String smsCountRefreshUnit = thirdPartyConfig.getSmsCountRefreshUnit();
                //  验证码获取次数变量
                String smsCountVar = thirdPartyConfig.getSmsCountVar();
                // 返回错误信息
                String msg = thirdPartyConfig.getSmsOverCountMsg()
                        .replace(smsCountRefreshTimeVar, smsCountRefreshTime.toString())
                        .replace(smsCountRefreshUnitVar, smsCountRefreshUnit)
                        .replace(smsCountVar, smsCount.toString());
                return ApiResult.error(msg);
            }
        }
        int code = RandomUtil.randomInt(thirdPartyConfig.getMinCode(), thirdPartyConfig.getMaxCode());
        Integer smsExpire = thirdPartyConfig.getSmsExpire();
        String smsContent = "";
        Long userCountByPhone = userService.getUserCountByPhone(phone);
        if (CommonConst.REGISTER_TYPE.equals(type)) {
            // 注册要判断当前手机号没有被注册
            if (userCountByPhone > 0) {
                return ApiResult.error("当前手机号已被注册");
            }
            redisUtils.hset(thirdPartyConfig.getSmsRegisterKey(), phone, code, smsExpire);
            smsContent = thirdPartyConfig.getSmsRegisterContent();
        } else if (CommonConst.LOGIN_TYPE.equals(type)) {
            // 登录要判断当前手机号已经被注册
            if (userCountByPhone == 0) {
                return ApiResult.error("当前手机号未注册，快去注册吧~");
            }
            redisUtils.hset(thirdPartyConfig.getSmsLoginKey(), phone, code, smsExpire);
            smsContent = thirdPartyConfig.getSmsLoginContent();
        } else if (CommonConst.FORGET_TYPE.equals(type)) {
            // 忘记密码要判断当前手机号已经被注册
            if (userCountByPhone == 0) {
                return ApiResult.error("当前手机号未注册，快去注册吧~");
            }
            redisUtils.hset(thirdPartyConfig.getSmsForgetKey(), phone, code, smsExpire);
            smsContent = thirdPartyConfig.getSmsForgetContent();
        } else {
            return ApiResult.error("验证码类型错误");
        }
        String smsCodeVar = thirdPartyConfig.getSmsCodeVar();
        String smsTimeVar = thirdPartyConfig.getSmsTimeVar();
        int expireMinute = smsExpire / 60;
        String sendSmsContent = smsContent.replace(smsCodeVar, Integer.toString(code)).replace(smsTimeVar, Integer.toString(expireMinute));
        // 如果缓存中没有当前手机获取验证码的次数就创建一个，并且设置过期时间
        if (!flag) {
            redisUtils.hset(thirdPartyConfig.getSmsSendCountKey(), phone, 0, thirdPartyConfig.getSmsCountRefresh());
        }
        // 验证码获取成功后，增加验证码获取次数
        redisUtils.hincr(thirdPartyConfig.getSmsSendCountKey(), phone, 1);
        return ApiResult.success(ResultInfo.SUCCESS, sendSmsContent);
    }


    @PostMapping("/register")
    @ApiOperation("注册接口")
    public ApiResult<LoginUser> register(@Valid @RequestBody RegisterVo registerVo, HttpServletRequest request) {
        // 判断两次密码是否一致
        if (!StrUtil.equals(registerVo.getPassword(), registerVo.getRePassword())) {
            return ApiResult.error("两次密码不一致");
        }
        // 首先判断当前手机号是否注册
        Long count = userService.getUserCountByPhone(registerVo.getPhone());
        if (count > 0) {
            return ApiResult.error("当前手机号已被注册");
        }
        // 判断当前用户名是否存在
        count = userService.getUserCountByUsername(registerVo.getUsername());
        if (count > 0) {
            return ApiResult.error("当前用户名已存在");
        }
        // 判断当前手机号是否获取验证码
        boolean flag = redisUtils.hHasKey(thirdPartyConfig.getSmsRegisterKey(), registerVo.getPhone());
        if (!flag) {
            return ApiResult.error("请先获取验证码");
        }
        // 判断验证码是否正确
        String code = redisUtils.hget(thirdPartyConfig.getSmsRegisterKey(), registerVo.getPhone()).toString();
        if (!StrUtil.equals(registerVo.getCode(), code)) {
            return ApiResult.error("验证码错误");
        }
        // 注册用户
        User registerUser = User.builder()
                // 用户名
                .username(registerVo.getUsername())
                // 昵称默认使用用户名，可修改
                .nickname(registerVo.getUsername())
                // 加密后的密码
                .password(bCryptPasswordEncoder.encode(registerVo.getPassword()))
                // 手机号码
                .phone(registerVo.getPhone())
                // 默认女生
                .gender(CommonConst.GIRL)
                // 默认使用女生头像地址
                .avatar(userConfig.getGirlAvatarUrl())
                .build();
        boolean result = false;
        try {
            registerUser.setLastLogin(LocalDateTime.now());
            registerUser.setLastIp(mineUtils.getIpAddress(request));
            result = userService.save(registerUser);
            if (result) {
                // 注册成功
                // 设置默认角色
                UserRole userRole = new UserRole();
                userRole.setUserId(registerUser.getId());
                userRole.setRoleId(userConfig.getUserDefaultRole());
                userRoleService.save(userRole);
                // 生成token
                String token = jwtUtils.generateToken(registerUser.getId() + CommonConst.SHORT_TRANSVERSE_LINE + registerUser.getUsername());
                registerUser.setPassword(null);
                // 获取权限
                String authorities = userService.getAuthorities(registerUser.getId(), registerUser.getUsername());
                LoginUser loginUser = LoginUser.builder()
                        .authority(authorities)
                        .token(token)
                        .user(registerUser)
                        .build();
                String loginUserJson = JSONUtil.toJsonStr(loginUser);
                // 将当前用户存入redis
                redisUtils.hset(userConfig.getLoginUserKey(), registerUser.getUsername(), loginUserJson, jwtConfig.getExpire());
                // 将当前验证码从缓存中删除
                redisUtils.hdel(thirdPartyConfig.getSmsRegisterKey(), registerVo.getPhone());
                return ApiResult.success("注册成功", loginUser);
            }
        } catch (Exception e) {
            log.info("注册失败，用户名或手机号可能重复{}，{}", registerVo.getUsername(), registerVo.getPhone());
            return ApiResult.error("用户名或手机号已存在");
        }
        return ApiResult.error("注册失败");
    }

    @Resource
    private FlowersTreesConfig flowersTreesConfig;

    @GetMapping("/test")
    @ApiOperation("更新缓存")
    public ApiResult<?> updateCache() {
        flowersTreesConfig.updateConfig(ConfigConst.JWT_CONFIG_CODE, JwtConfig.class, jwtConfig);
        flowersTreesConfig.updateConfig(ConfigConst.THIRD_PARTY_CODE, ThirdPartyConfig.class, thirdPartyConfig);
        flowersTreesConfig.updateConfig(ConfigConst.USER_INFO_CODE, UserConfig.class, userConfig);
        return ApiResult.success();
    }
}
