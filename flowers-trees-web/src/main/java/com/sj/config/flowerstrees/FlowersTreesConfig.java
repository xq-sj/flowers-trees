package com.sj.config.flowerstrees;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sj.config.flowerstrees.jwt.JwtConfig;
import com.sj.config.flowerstrees.user.UserConfig;
import com.sj.constant.CommonConst;
import com.sj.constant.ConfigConst;
import com.sj.entity.DictData;
import com.sj.entity.DictType;
import com.sj.entity.User;
import com.sj.service.DictDataService;
import com.sj.service.DictTypeService;
import com.sj.utils.redis.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName InitConfig
 * @Author 闪光灯
 * @Date 2023/7/27 16:11
 * @Description 花树项目配置
 */
@Data
@Configuration
@Slf4j
public class FlowersTreesConfig {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataService dictDataService;

    /**
     * jwt工具配置
     */
    private JwtConfig jwtConfig;

    /**
     * 用户配置
     */
    private UserConfig userConfig;

    public FlowersTreesConfig() {
        log.info("正在初始化项目配置...");
    }

    @Bean
    public JwtConfig jwtConfig() {
        log.info("正在注册jwt配置项...");
        jwtConfig = new JwtConfig();
        buildJwtConfig();
        log.info("jwt配置完成");
        return jwtConfig;
    }

    @Bean
    public UserConfig userConfig() {
        log.info("正在注册用户配置项...");
        userConfig = new UserConfig();
        buildUserConfig();
        log.info("用户配置完成");
        return userConfig;
    }

    //===========================用户配置开始==============================

    /**
     * 更新用户配置
     */
    public void updateUserConfig() {
        // 清除redis缓存
        // 首先查看redis中有没有缓存
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, ConfigConst.USER_INFO_CODE);
        if (flag) {
            log.info("正在清除缓存...");
            redisUtils.hdel(ConfigConst.FLOWERS_TREES, ConfigConst.USER_INFO_CODE);
        }
        buildJwtConfig();
        log.info("jwt配置已更新");
    }

    /**
     * 设置用户配置
     */
    private void buildUserConfig() {
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, ConfigConst.USER_INFO_CODE);
        if (flag) {
            log.info("正在读取缓存...");
            // 如果有，获取redis中的用户相关配置
            String userConfigJson = redisUtils.hget(ConfigConst.FLOWERS_TREES, ConfigConst.USER_INFO_CODE).toString();
            // 将json转换为UserConfig.class
            UserConfig userConfigCache = JSONUtil.toBean(userConfigJson, UserConfig.class);
            if (userConfigCache != null) {
                BeanUtil.copyProperties(userConfigCache, userConfig);
                return;
            }
        }
        // 如果没有，查询数据库
        log.info("正在获取配置...");
        // 如果没有，查询字典表，设置jwt配置后将查询结果存入redis
        DictType userDictType = dictTypeService.lambdaQuery().eq(DictType::getDictCode, ConfigConst.USER_INFO_CODE).one();
        List<DictData> list = dictDataService.lambdaQuery().eq(DictData::getDictTypeId, userDictType.getId()).eq(DictData::getStatus, CommonConst.ACTIVE).list();
        list.forEach(item -> {
            // 权限缓存key
            if (ConfigConst.AUTH_CACHE_KEY.equals(item.getDictDataCode())) {
                userConfig.setAuthCacheKey(item.getDictValue());
            }
            // 登录用户key
            if (ConfigConst.LOGIN_USER_KEY.equals(item.getDictDataCode())) {
                userConfig.setLoginUserKey(item.getDictValue());
            }
        });
        // 设置完成，保存当前配置对象到redis
        String userConfigJson = JSONUtil.toJsonStr(userConfig);
        redisUtils.hset(ConfigConst.FLOWERS_TREES, ConfigConst.USER_INFO_CODE, userConfigJson);
    }
    //===========================用户配置结束==============================


    //===========================jwt配置开始==============================

    /**
     * 更新jwt配置
     */
    public void updateJwtConfig() {
        // 清除redis缓存
        // 首先查看redis中有没有缓存
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, ConfigConst.JWT_CONFIG_CODE);
        if (flag) {
            log.info("正在清除缓存...");
            redisUtils.hdel(ConfigConst.FLOWERS_TREES, ConfigConst.JWT_CONFIG_CODE);
        }
        buildJwtConfig();
        log.info("jwt配置已更新");
    }

    /**
     * 设置jwt配置
     */
    private void buildJwtConfig() {
        // 首先查看redis中有没有缓存
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, ConfigConst.JWT_CONFIG_CODE);
        if (flag) {
            log.info("正在读取缓存...");
            // 如果有, 获取存进redis中的json字符串
            String jwtConfigJson = redisUtils.hget(ConfigConst.FLOWERS_TREES, ConfigConst.JWT_CONFIG_CODE).toString();
            // 将json转为JwtConfig.class
            JwtConfig jwtConfigCache = JSONUtil.toBean(jwtConfigJson, JwtConfig.class);
            if (jwtConfigCache != null) {
                BeanUtil.copyProperties(jwtConfigCache, jwtConfig);
                return;
            }
        }
        log.info("正在获取配置...");
        // 如果没有，查询字典表，设置jwt配置后将查询结果存入redis
        DictType jwtDictType = dictTypeService.lambdaQuery().eq(DictType::getDictCode, ConfigConst.JWT_CONFIG_CODE).one();
        List<DictData> list = dictDataService.lambdaQuery().eq(DictData::getDictTypeId, jwtDictType.getId()).eq(DictData::getStatus, CommonConst.ACTIVE).list();
        list.forEach(item -> {
            // 密钥
            if (ConfigConst.JWT_CONFIG_SECRET.equals(item.getDictDataCode())) {
                jwtConfig.setSecret(item.getDictValue());
            }
            // 过期时间
            if (ConfigConst.JWT_CONFIG_EXPIRE.equals(item.getDictDataCode())) {
                jwtConfig.setExpire(Long.valueOf(item.getDictValue()));
            }
            // 请求头
            if (ConfigConst.JWT_CONFIG_HEADER.equals(item.getDictDataCode())) {
                jwtConfig.setHeader(item.getDictValue());
            }
            // 前缀
            if (ConfigConst.JWT_CONFIG_PREFIX.equals(item.getDictDataCode())) {
                jwtConfig.setPrefix(item.getDictValue());
            }
            // jwt过期前多久刷新 单位秒(s)
            if (ConfigConst.JWT_CONFIG_REFRESH.equals(item.getDictDataCode())) {
                jwtConfig.setRefresh(Long.valueOf(item.getDictValue()));
            }
        });
        // 设置完成，保存当前配置对象到redis
        String jwtConfigJson = JSONUtil.toJsonStr(jwtConfig);
        redisUtils.hset(ConfigConst.FLOWERS_TREES, ConfigConst.JWT_CONFIG_CODE, jwtConfigJson);
    }

    //===========================jwt配置结束==============================

}
