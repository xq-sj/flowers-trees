package com.sj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sj.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户权限用,分割
     *
     * @param id       用户id
     * @param username 用户名
     * @return 权限信息
     */
    String getAuthorities(Integer id, String username);

    /**
     * 根据手机号获取用户个数
     *
     * @param phone 手机号码
     * @return 用户个数
     */
    Long getUserCountByPhone(String phone);

    /**
     * 根据用户名获取用户个数
     *
     * @param username 用户名
     * @return 用户个数
     */
    Long getUserCountByUsername(String username);
}
