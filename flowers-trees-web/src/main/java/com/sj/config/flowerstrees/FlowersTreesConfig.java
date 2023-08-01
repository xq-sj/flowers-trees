package com.sj.config.flowerstrees;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sj.config.flowerstrees.third.ThirdPartyConfig;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName InitConfig
 * @Author 闪光灯
 * @Date 2023/7/27 16:11
 * @Description 花树项目配置 注意配置类的属性变量名必须与字典表的各个配置项的编码(dict_data->dict_data_code)相同
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

    /**
     * 认证相关配置
     */
    private ThirdPartyConfig thirdPartyConfig;

    public FlowersTreesConfig() {
        log.info("正在初始化项目配置...");
    }

    @Bean
    public JwtConfig jwtConfig() {
        log.info("正在注册jwt配置项...");
        jwtConfig = new JwtConfig();
        buildConfig(ConfigConst.JWT_CONFIG_CODE, JwtConfig.class, jwtConfig);
        log.info("jwt配置完成");
        return jwtConfig;
    }

    @Bean
    public UserConfig userConfig() {
        log.info("正在注册用户配置项...");
        userConfig = new UserConfig();
        buildConfig(ConfigConst.USER_INFO_CODE, UserConfig.class, userConfig);
        log.info("用户配置完成");
        return userConfig;
    }

    @Bean
    public ThirdPartyConfig thirdPartyConfig() {
        log.info("正在注册第三方相关配置项...");
        thirdPartyConfig = new ThirdPartyConfig();
        buildConfig(ConfigConst.THIRD_PARTY_CODE, ThirdPartyConfig.class, thirdPartyConfig);
        log.info("第三方相关配置完成");
        return thirdPartyConfig;
    }

    /**
     * 从缓存或数据库中读取指定的配置项
     *
     * @param configCode 配置的编码
     * @param configBean 配置的对象类型
     * @param source     需要先将读取的项放置在的java对象
     */
    private void buildConfig(String configCode, Class<?> configBean, Object source) {
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, configCode);
        if (flag) {
            log.info("正在读取缓存...");
            // 如果有，获取redis中的用户相关配置
            String thirdPartyConfigJson = redisUtils.hget(ConfigConst.FLOWERS_TREES, configCode).toString();
            // 将json转换为ThirdPartyConfig.class
            Object configCache = JSONUtil.toBean(thirdPartyConfigJson, configBean);
            if (configCache != null) {
                BeanUtil.copyProperties(configCache, source);
                return;
            }
        }
        // 如果没有，查询数据库
        log.info("正在获取配置...");
        // 如果没有，查询字典表，设置jwt配置后将查询结果存入redis
        DictType dictType = dictTypeService.lambdaQuery().eq(DictType::getDictCode, configCode).one();
        List<DictData> list = dictDataService.lambdaQuery().eq(DictData::getDictTypeId, dictType.getId()).eq(DictData::getStatus, CommonConst.ACTIVE).list();
        list.forEach(item -> {
            try {
                // 根据反射对象对象获取当前配置项的变量
                Field declaredField = configBean.getDeclaredField(item.getDictDataCode());
                // 开启爆破设置属性值
                declaredField.setAccessible(true);
                // 如果当前属性是数值类型
                if (CommonConst.YES.equals(item.getIsNumber())) {
                    Integer number = Integer.valueOf(item.getDictValue());
                    declaredField.set(source, number);
                } else {
                    declaredField.set(source, item.getDictValue());
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.info("{}中{}属性未找到", configCode, item);
            }
        });
        // 设置完成，保存当前配置对象到redis
        String configJson = JSONUtil.toJsonStr(source);
        redisUtils.hset(ConfigConst.FLOWERS_TREES, configCode, configJson);
    }

    /**
     * 更新配置
     */
    public void updateConfig(String configCode, Class<?> configBean, Object source) {
        // 清除redis缓存
        // 首先查看redis中有没有缓存
        boolean flag = redisUtils.hHasKey(ConfigConst.FLOWERS_TREES, configCode);
        if (flag) {
            log.info("正在清除缓存...");
            redisUtils.hdel(ConfigConst.FLOWERS_TREES, configCode);
        }
        buildConfig(configCode, configBean, source);
        String dictName = dictTypeService.getDictNameByCode(configCode);
        if (dictName != null) {
            log.info("{}已更新", dictName);
        }
    }


}
